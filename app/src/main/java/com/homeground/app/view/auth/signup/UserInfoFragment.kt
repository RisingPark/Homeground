package com.homeground.app.view.auth.signup


import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.fragment.app.Fragment

import com.homeground.app.common.base.BaseFragment
import com.homeground.app.databinding.FragmentUserInfoBinding
import com.homeground.app.view.main.model.UserInfoViewModel
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.app.DatePickerDialog
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class UserInfoFragment : BaseFragment<FragmentUserInfoBinding, UserInfoViewModel>() {

    companion object {
        fun newInstance() = UserInfoFragment()
    }

    override val layoutResourceId: Int
        get() = com.homeground.app.R.layout.fragment_user_info
    override val vm: UserInfoViewModel by viewModel()

    override fun initStartView() {
        setToolbar()
        setEditText()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun setToolbar() {
        common_toolbar_title_text.text = getString(com.homeground.app.R.string.sign_up)
        common_toolbar_back_image.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setEditText() {
        phone_edit.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        val onDateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                birthday_edit.setText("$year-$month-$dayOfMonth")
            }


        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)-1
        val dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH)

        birthday_layout.setOnClickListener {
            val dialog = DatePickerDialog(activity,android.R.style.Theme_Holo_Light_Dialog, onDateSetListener, year, month, dayOfMonth)
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
        }
    }
}
