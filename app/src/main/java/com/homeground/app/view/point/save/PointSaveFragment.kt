package com.homeground.app.view.point.save


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.homeground.app.R
import com.homeground.app.common.DialogHelper
import com.homeground.app.common.Utils
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.common.interfaces.OnDialogResultListener
import com.homeground.app.common.ui.textview.ScaleTextView
import com.homeground.app.databinding.FragmentUserInfoBinding
import com.homeground.app.view.point.save.model.PointSaveViewModel
import com.homeground.app.view.point.search.PointSearchActivity
import com.homeground.app.view.point.search.PointSearchFragment
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
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
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class PointSaveFragment : BaseFragment<FragmentUserInfoBinding, PointSaveViewModel>() {

    private var resultNum: String = ""
    private var resultPoint: String = ""
    private var mType: Int? = 0
    private lateinit var mUser: UserInfoResponseDTO

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
            POINT_SAVE -> {
                setPointSave()
            }
            POINT_USE -> {
                setPointUse()
            }
        }
        setNumberKeyPad()
        initUserInfo()
    }

    override fun initDataBinding() {
        vm.pointSaveLiveData.observe(this, Observer {
            hideLoadingProgress()
            if (it.isSuccess){
                val oldPoint = mUser.point
                mUser = it
                (activity as PointSearchActivity).changeUserInfo(it) // 리스트 갱신
                DialogHelper.showCommonDialog(
                    getBaseActivity(),
                    "$resultPoint P ${getFinishText()}\n\n $oldPoint P -> ${it.point} P ",
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
    }

    override fun initAfterBinding() {

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
            vm.savePoint(POINT_SAVE, mUser, resultPoint)
        }
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
            vm.savePoint(POINT_USE, mUser, resultPoint)
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
        point_point_text.text = "${mUser.point.toString()} P"
        point_last_point_date_text.text = mUser.last_point_date
    }

    private fun setNumberKeyPad() {
        val numBtn: Array<ScaleTextView> =
            arrayOf(key_0, key_00, key_1, key_2, key_3, key_4, key_5, key_6, key_7, key_8, key_9)
        for (view in numBtn) {
            view.setOnClickListener {
                when (mType) {
                    POINT_SAVE -> {
                        if (resultNum.length > 10
                            || (resultNum == ""
                                    && (view.text.toString() == "0" || view.text.toString() == "00"))
                        ) return@setOnClickListener

                        resultNum += view.text.toString()
                        point_money_text.text = Utils.addComma(resultNum.toLong()) + " 원"
                        resultPoint = calPoint(resultNum)
                        point_num_text.text = Utils.addCommaP(resultPoint)
                    }
                    POINT_USE -> {
                        if (resultPoint.length > 10
                            || (resultPoint == ""
                                    && (view.text.toString() == "0" || view.text.toString() == "00"))
                        ) return@setOnClickListener

                        resultPoint += view.text.toString()
                        point_num_text.text = Utils.addCommaP(resultPoint)
                    }
                }
            }
        }

        key_del.setOnClickListener {
            when (mType) {
                POINT_SAVE -> {
                    if (resultNum.isEmpty()) return@setOnClickListener

                    resultNum = resultNum.substring(0, resultNum.length - 1)
                    point_money_text.text =
                        if (resultNum.isNotEmpty()) Utils.addComma(resultNum.toLong()) + " 원" else "0 원"
                    resultPoint = calPoint(resultNum)
                    point_num_text.text =
                        if (resultNum.isNotEmpty()) Utils.addCommaP(resultPoint) else "0 P"
                }
                POINT_USE -> {
                    if (resultPoint.isEmpty()) return@setOnClickListener

                    resultPoint = resultPoint.substring(0, resultPoint.length - 1)
                    point_num_text.text =
                        if (resultPoint.isNotEmpty()) Utils.addCommaP(resultPoint) else "0 P"
                }
            }
        }

        key_fun_1.setOnClickListener {
            clearNumber()
        }
    }

    private fun clearNumber() {
        if (resultNum.isNotEmpty() || resultPoint.isNotEmpty()) {
            resultNum = ""
            resultPoint = ""
            point_money_text.text = "0 원"
            point_num_text.text = "0 P"
        }
    }

    private fun calPoint(num: String): String {
        if (num.isEmpty()) return ""

        return (num.toDouble() * 0.05).toLong().toString()
    }

    private fun getFinishText(): String {
        return if (mType == POINT_SAVE) "적립완료" else "사용완료"
    }

    private fun refreshPoint(user: UserInfoResponseDTO){
        clearNumber()
        point_point_text.text = "${user.point.toString()} P"
        point_last_point_date_text.text = user.last_point_date
    }
}
