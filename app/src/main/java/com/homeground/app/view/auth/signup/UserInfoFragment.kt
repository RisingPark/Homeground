package com.homeground.app.view.auth.signup


import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.fragment.app.Fragment

import com.homeground.app.common.base.BaseFragment
import com.homeground.app.databinding.FragmentUserInfoBinding
import com.homeground.app.view.auth.signup.model.UserInfoViewModel
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.homeground.app.R
import com.homeground.app.common.DialogHelper
import com.homeground.app.common.Utils
import com.homeground.app.common.interfaces.OnDialogResultListener
import com.homeground.app.view.point.save.PointSaveFragment
import com.homeground.app.view.point.search.PointSearchActivity
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class UserInfoFragment : BaseFragment<FragmentUserInfoBinding, UserInfoViewModel>() {

    private var mType: Int? = 0
    private var mUser: UserInfoResponseDTO? = null
    private var preBirthday: String = ""

    companion object {
        const val SIGN_UP = 0
        const val MODIFY = 1
        const val KEY_TYPE = "user_info_type"
        const val KEY_USER = "user_info_user"

        fun newInstance(type: Int) = UserInfoFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_TYPE, type)
            }
        }
        fun newInstance(type: Int, user: UserInfoResponseDTO) = UserInfoFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_TYPE, type)
                putSerializable(KEY_USER, user)
            }
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_user_info
    override val vm: UserInfoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { it ->
            mType = it.getInt(KEY_TYPE, 0)
            it.getSerializable(KEY_USER)?.let {
                mUser = it as UserInfoResponseDTO
            }
        }
    }

    override fun initStartView() {
        setToolbar()
        setEditText()
        checkType()
    }

    override fun initDataBinding() {
        vm.signUpUserLiveData.observe(this, androidx.lifecycle.Observer {
            hideLoadingProgress()
            if (it.isSuccess) {
                var msg = name_edit.text.toString()+"님\n"+getString(R.string.sign_up_finish)
                DialogHelper.showCommonDialog(getBaseActivity(), msg, object : OnDialogResultListener{
                    override fun resultSuccess(data: String) {
                        activity?.finish()
                    }
                    override fun resultFailure(failMsg: String) {}
                    override fun resultCancel() {}
                })
            } else {
                var msg = getString(R.string.sign_up_fail)+"\n내용 : "+ it.msg
                DialogHelper.showCommonDialog(getBaseActivity(), msg, null)
            }
        })

        vm.modifyUserLiveData.observe(this, androidx.lifecycle.Observer {
            hideLoadingProgress()
            if (it.isSuccess) {
                (activity as PointSearchActivity).changeUserInfo(it) // 리스트 갱신
                var msg = name_edit.text.toString()+"님\n"+getString(R.string.modify_finish)
                DialogHelper.showCommonDialog(getBaseActivity(), msg, object : OnDialogResultListener{
                    override fun resultSuccess(data: String) {
                        activity?.onBackPressed()
                    }
                    override fun resultFailure(failMsg: String) {}
                    override fun resultCancel() {}
                })
            } else {
                var msg = getString(R.string.modify_fail)+"\n내용 : "+ it.msg
                DialogHelper.showCommonDialog(getBaseActivity(), msg, null)
            }
        })
    }

    override fun initAfterBinding() {
    }

    private fun setToolbar() {
        val title = if (mType == SIGN_UP)getString(R.string.sign_up) else getString(R.string.user_info_modify)
        common_toolbar_title_text.text = title
        common_toolbar_back_image.setOnClickListener {
            activity?.onBackPressed()
        }

        common_toolbar_right_text.apply {
            when(mType){
                SIGN_UP -> {
                    text= getString(R.string.do_join)
                    visibility = View.VISIBLE
                    setOnClickListener {
                        if (isValid()){
                            showLoadingProgress()
                            vm.setSignUpUser(
                                name_edit.text.toString(),
                                phone_edit.text.toString(),
                                birthday_edit.text.toString(),
                                note_edit.text.toString())
                        }
                    }
                }
                MODIFY -> {
                    text= getString(R.string.do_modify)
                    visibility = View.VISIBLE
                    setOnClickListener {
                        if (isValid()) {
                            showLoadingProgress()
                            mUser?.let { it1 ->
                                vm.setModifyUser(
                                    it1,
                                    name_edit.text.toString(),
                                    phone_edit.text.toString(),
                                    birthday_edit.text.toString(),
                                    note_edit.text.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkType() {
        when (mType) {
            SIGN_UP -> {

            }
            MODIFY -> {
                name_edit.setText(mUser?.name)
                phone_edit.setText(mUser?.phone)
                birthday_edit.setText(mUser?.birthday)
                note_edit.setText(mUser?.note)
                preBirthday = mUser?.birthday.toString()
            }
        }
    }

    private fun setEditText() {
        phone_edit.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        val onDateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                preBirthday = "$year-${month+1}-$dayOfMonth"
                birthday_edit.setText(preBirthday)
            }

        birthday_layout.setOnClickListener {
            var year = 0
            var month = 0
            var dayOfMonth = 0
            if (preBirthday.isEmpty()){
                val currentDate = Calendar.getInstance()
                year = currentDate.get(Calendar.YEAR)
                month = currentDate.get(Calendar.MONTH)
                dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH)
            } else {
                val currentDate =  preBirthday.split("-")
                year = currentDate[0].toInt()
                month = currentDate[1].toInt() -1
                dayOfMonth = currentDate[2].toInt()
            }
            val dialog = DatePickerDialog(activity, android.R.style.Theme_Holo_Light_Dialog, onDateSetListener, year, month, dayOfMonth)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
        }
    }

    private fun isValid() : Boolean {
        var notValidText = ""
        var isValid = true
        when {
            Utils.isEmpty(name_edit.text.toString()) -> {
                notValidText = "${getString(R.string.name)}을 입력해주세요"
                isValid = false
            }
            Utils.isEmpty(phone_edit.text.toString()) -> {
                notValidText = "${getString(R.string.phone_num)}를 입력해주세요"
                isValid = false
            }
            !Utils.isValidPhoneNumber(phone_edit.text.toString()) -> {
                notValidText = "${getString(R.string.phone_num)}가 유효하지 않습니다."
                isValid = false
            }
        }
        if (!isValid){
            Toast.makeText(activity, notValidText, Toast.LENGTH_SHORT).show()
            return isValid
        }
        return true
    }

}
