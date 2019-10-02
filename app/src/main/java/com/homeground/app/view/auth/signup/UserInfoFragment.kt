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
import android.view.View
import android.widget.Toast
import com.homeground.app.R
import com.homeground.app.common.DialogHelper
import com.homeground.app.common.Utils
import com.homeground.app.common.interfaces.OnDialogResultListener
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class UserInfoFragment : BaseFragment<FragmentUserInfoBinding, UserInfoViewModel>() {

    companion object {
        fun newInstance() = UserInfoFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_user_info
    override val vm: UserInfoViewModel by viewModel()

    override fun initStartView() {
        setToolbar()
        setEditText()
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
    }

    override fun initAfterBinding() {
    }

    private fun setToolbar() {
        common_toolbar_title_text.text = getString(com.homeground.app.R.string.sign_up)
        common_toolbar_back_image.setOnClickListener {
            activity?.onBackPressed()
        }
        common_toolbar_right_text.apply {
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

    }

    private fun setEditText() {
        phone_edit.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        val onDateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                birthday_edit.setText("$year-${month+1}-$dayOfMonth")
            }

        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH)

        birthday_layout.setOnClickListener {
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
