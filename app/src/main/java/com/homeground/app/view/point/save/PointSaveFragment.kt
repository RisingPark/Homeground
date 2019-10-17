package com.homeground.app.view.point.save


import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.homeground.app.R
import com.homeground.app.common.DialogHelper
import com.homeground.app.common.Preference
import com.homeground.app.common.Utils
import com.homeground.app.common.base.BaseActivity
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.common.interfaces.OnDialogResultListener
import com.homeground.app.common.interfaces.OnItemClickListener
import com.homeground.app.common.ui.textview.ScaleTextView
import com.homeground.app.databinding.FragmentUserInfoBinding
import com.homeground.app.view.point.save.adapter.PointHistoryAdapter
import com.homeground.app.view.point.save.bean.PointInfoResponseDTO
import com.homeground.app.view.point.save.model.PointSaveViewModel
import com.homeground.app.view.point.search.PointSearchActivity
import com.homeground.app.view.point.search.PointSearchFragment
import com.homeground.app.view.point.search.adapter.UserListRecyclerViewAdapter
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_point_save.*

import kotlinx.android.synthetic.main.layout_common_toolbar.*
import kotlinx.android.synthetic.main.layout_cal_pad.*
import kotlinx.android.synthetic.main.layout_cal_pad.key_0
import kotlinx.android.synthetic.main.layout_cal_pad.key_1
import kotlinx.android.synthetic.main.layout_cal_pad.key_2
import kotlinx.android.synthetic.main.layout_cal_pad.key_3
import kotlinx.android.synthetic.main.layout_cal_pad.key_4
import kotlinx.android.synthetic.main.layout_cal_pad.key_5
import kotlinx.android.synthetic.main.layout_cal_pad.key_6
import kotlinx.android.synthetic.main.layout_cal_pad.key_7
import kotlinx.android.synthetic.main.layout_cal_pad.key_8
import kotlinx.android.synthetic.main.layout_cal_pad.key_9
import kotlinx.android.synthetic.main.layout_cal_pad.key_del
import kotlinx.android.synthetic.main.layout_number_pad.*
import kotlinx.android.synthetic.main.layout_point_history_item.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class PointSaveFragment : BaseFragment<FragmentUserInfoBinding, PointSaveViewModel>() {

    private var resultNum: String = ""
    private var resultPoint: String = ""
    private var mType: Int? = 0
    private var isPointInput: Boolean = false
    private var isCanceled: Boolean = false
    private var cancelPoint = ""

    private lateinit var mUser: UserInfoResponseDTO
    private val mPointHistoryAdapter: PointHistoryAdapter by inject()

    companion object {
        const val POINT_SAVE = 0
        const val POINT_USE = 1
        const val KEY_TYPE = "point_type"
        const val KEY_USER = "point_user"

        fun newInstance(type: Int, user: UserInfoResponseDTO) = PointSaveFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_TYPE, type)
                putSerializable(KEY_USER, user)
            }
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_point_save
    override val vm: PointSaveViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mType = it.getInt(KEY_TYPE, 0)
            mUser = it.getSerializable(KEY_USER) as UserInfoResponseDTO
        }
    }

    override fun initStartView() {
        when (mType) {
            POINT_SAVE -> { // 포인트 적립
                setPointSave()
            }
            POINT_USE -> {  // 포인트 사용
                setPointUse()
            }
        }
        setNumberKeyPad()
        initUserInfo()
    }

    override fun initDataBinding() {
        // 적립/사용
        vm.pointSaveLiveData.observe(this, Observer {
            hideLoadingProgress()
            if (it.isSuccess){
                val oldPoint = mUser.point
                mUser = it
                (activity as PointSearchActivity).changeUserInfo(it) // 리스트 갱신
                var resultText = ""
                if (isCanceled){
                    isCanceled = false
                    resultText = "$cancelPoint 취소완료\n\n $oldPoint P -> ${it.point} P "
                } else {
                    resultText = "$resultPoint P ${getFinishText()}\n\n $oldPoint P -> ${it.point} P "
                }

                DialogHelper.showCommonDialog(
                    getBaseActivity(),
                    resultText,
                    object : OnDialogResultListener {
                        override fun resultSuccess(data: String) {
                            refreshPoint(it)
                        }

                        override fun resultFailure(failMsg: String) {
                            refreshPoint(it)
                        }

                        override fun resultCancel() {
                            refreshPoint(it)
                        }
                    })
            } else {
                DialogHelper.showCommonDialog(getBaseActivity(), it.msg, null)
            }
        })

        // 포인트 내역
        vm.pointHistoryLiveData.observe(this, Observer {
            hideLoadingProgress()
            if (it.isSuccess) {
                point_recycler_view.run {
                    adapter = mPointHistoryAdapter.apply {
                        this.items = it?.pointInfoResponseDTO
                        onPointCancelClickListener = object : OnItemClickListener { // 취소 버튼
                            override fun onItemClickListener(view: View, position: Int) {
                                activity?.let {
                                    var msg = ""
                                    view.run {
                                        msg = point_history_point_text.text.toString()+" "+point_history_state_text.text.toString() + getString(R.string._cancel)
                                        cancelPoint = point_history_point_text.text.toString()
                                    }
                                    DialogHelper.showCommonDialog(it as BaseActivity, msg, getString(R.string.cancel), object :OnDialogResultListener{
                                        override fun resultSuccess(data: String) {
                                            isCanceled = true
                                            showLoadingProgress()
                                            var copyUser = mUser.copy()
                                            vm.cancelPoint(copyUser, items, position)
                                        }

                                        override fun resultFailure(failMsg: String) {
                                            cancelPoint = ""
                                        }

                                        override fun resultCancel() {
                                        }
                                    })
                                }
                            }
                        }
                    }
                }
            } else {
                DialogHelper.showCommonDialog(getBaseActivity(), it.msg, null)
            }
        })
    }

    override fun initAfterBinding() {
        vm.getPointHistory(mUser)
    }

    /**
     * 포인트 적립
     */
    private fun setPointSave() {
        setToolbar(getString(R.string.point_save))
        point_done_btn.text = getString(R.string.save)
        point_done_btn.setOnClickListener {
            if (resultPoint.isEmpty() || resultPoint.toLong() < 1) return@setOnClickListener

            showLoadingProgress()
            activity?.let {
                vm.savePoint(POINT_SAVE, mUser, Preference.getDeviceName(it), resultPoint)
            }
        }
        setPointSaveKeyPad()
    }

    /**
     * 포인트 사용
     */
    private fun setPointUse() {
        setToolbar(getString(R.string.point_use))
        point_money_text.visibility = View.GONE
        point_done_btn.text = getString(R.string.use)
        point_done_btn.setOnClickListener {
            if (resultPoint.isEmpty() || resultPoint.toLong() < 1) return@setOnClickListener

            showLoadingProgress()
            activity?.let {
                vm.savePoint(POINT_USE, mUser, Preference.getDeviceName(it), resultPoint)
            }
        }
    }

    private fun setToolbar(title: String) {
        common_toolbar_title_text.text = title
        common_toolbar_back_image.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    /**
     * 유저 정보 초기화
     */
    private fun initUserInfo() {
        point_name_text.text = mUser.name
        point_birthday_text.text = "(${mUser.birthday})"
        point_point_text.text = Utils.addCommaP(mUser.point.toString())
    }

    private fun setNumberKeyPad() {
        val numBtn: Array<ScaleTextView> =
            arrayOf(key_0, key_00, key_1, key_2, key_3, key_4, key_5, key_6, key_7, key_8, key_9)
        for (view in numBtn) {
            view.setOnClickListener {
                when (mType) {
                    POINT_SAVE -> {
                        if (isPointInput){
                            if (!inputPointNum(it as TextView?)) return@setOnClickListener
                        } else{
                            if (!inputMoneyNum(it as TextView?)) return@setOnClickListener
                        }
                    }
                    POINT_USE -> {
                        if (!inputPointNum(it as TextView?)) return@setOnClickListener
                    }
                }
            }
        }

        key_del.setOnClickListener {
            when (mType) {
                POINT_SAVE -> {
                    if (isPointInput){
                       delPointNum()
                    } else {
                       delMoneyNum()
                    }

                }
                POINT_USE -> {
                    delPointNum()
                }
            }
        }

        key_fun_1.setOnClickListener {
            clearNumber()
        }
    }

    /**
     * 데이터 클리어
     */
    private fun clearNumber() {
        if (resultNum.isNotEmpty() || resultPoint.isNotEmpty()) {
            resultNum = ""
            resultPoint = ""
            point_money_text.text = "0 원"
            point_num_text.text = "0 P"
        }
    }

    /**
     * 포인트 계산하기
     */
    private fun calPoint(num: String): String {
        if (num.isEmpty()) return ""

        return (num.toDouble() * 0.01 * Preference.getPercent(activity!!).toDouble()).toLong().toString()
    }

    /**
     * 완료 텍스트
     */
    private fun getFinishText(): String {
        return if (mType == POINT_SAVE) "적립완료" else "사용완료"
    }

    /**
     * 포인트 갱신
     */
    private fun refreshPoint(user: UserInfoResponseDTO){
        clearNumber()
        point_point_text.text = Utils.addCommaP(user.point.toString())
    }

    /**
     * 포인트 적립 패드 설정
     */
    private fun setPointSaveKeyPad() {
        setPointOrMoneyText(isPointInput)
        key_percent.setOnClickListener {
            clearNumber()
            isPointInput = !isPointInput
            setPointOrMoneyText(isPointInput)
            point_money_text.visibility = if (isPointInput) View.GONE else View.VISIBLE
        }
    }

    private fun setPointOrMoneyText(isPointInput: Boolean) {
        val str = if (isPointInput){
            activity?.let { "포인트 적립" }.toString()
        } else {
            activity?.let { "금액 적립\n(${Preference.getPercent(it)}%)" }.toString()
        }
        key_percent.text = str
    }

    private fun inputPointNum(view: TextView?): Boolean{
        if (resultPoint.length > 10
            || (resultPoint == ""
                    && (view?.text.toString() == "0" || view?.text.toString() == "00"))
        ) return false

        resultPoint += view?.text.toString()
        point_num_text.text = Utils.addCommaP(resultPoint)
        return true
    }

    private fun inputMoneyNum(view: TextView?): Boolean{
        if (resultNum.length > 10
            || (resultNum == ""
                    && (view?.text.toString() == "0" || view?.text.toString() == "00"))
        ) return false

        resultNum += view?.text.toString()
        point_money_text.text = Utils.addComma(resultNum.toLong()) + " 원"
        resultPoint = calPoint(resultNum)
        point_num_text.text = Utils.addCommaP(resultPoint)
        return true
    }

    private fun delPointNum(): Boolean{
        if (resultPoint.isEmpty()) return false

        resultPoint = resultPoint.substring(0, resultPoint.length - 1)
        point_num_text.text = if (resultPoint.isNotEmpty()) Utils.addCommaP(resultPoint) else "0 P"
        return true
    }

    private fun delMoneyNum(): Boolean{
        if (resultNum.isEmpty()) return false

        resultNum = resultNum.substring(0, resultNum.length - 1)
        point_money_text.text =
            if (resultNum.isNotEmpty()) Utils.addComma(resultNum.toLong()) + " 원" else "0 원"
        resultPoint = calPoint(resultNum)
        point_num_text.text =
            if (resultNum.isNotEmpty()) Utils.addCommaP(resultPoint) else "0 P"
        return true
    }
}
