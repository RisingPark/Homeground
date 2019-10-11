package com.homeground.app.view.settings


import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.homeground.app.BuildConfig

import com.homeground.app.common.Preference
import com.homeground.app.common.Utils
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.databinding.FragmentUserInfoBinding
import com.homeground.app.view.settings.model.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.Context.INPUT_METHOD_SERVICE
import android.R



/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : BaseFragment<FragmentUserInfoBinding, SettingsViewModel>() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override val layoutResourceId: Int
        get() = com.homeground.app.R.layout.fragment_settings
    override val vm: SettingsViewModel by viewModel()

    override fun initStartView() {
        setToolbar()
        setVersion()
        setPercent()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
    }


    private fun setToolbar() {
        common_toolbar_title_text.text = getString(com.homeground.app.R.string.settings)
        common_toolbar_back_image.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setVersion(){
        settings_current_version.text = activity?.let { Utils.getApplicationVersionName(it, BuildConfig.VERSION_NAME) }
    }

    private fun setPercent(){
        activity?.let { percent_edit.setText(Preference.getPercent(it).toString()) }

        // 수정
        settings_confirm_btn.setOnClickListener {
            it.isEnabled = false
            percent_edit.isEnabled = true
            percent_edit.isFocusableInTouchMode = true
            percent_edit.requestFocus()
            if (activity != null) {
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(
                    InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
                )
            }
            percent_edit.setSelection(0, percent_edit.text.toString().length)
        }


        percent_edit.run {
            isClickable = false
            isFocusableInTouchMode = false
        }
        percent_edit.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE    // 키보드 완료 버튼
                -> {
                    percent_edit.clearFocus()
                    percent_edit.isClickable = false
                    percent_edit.isFocusableInTouchMode = false
                    settings_confirm_btn.isEnabled = true
                    activity?.let {
                        Preference.setPercent(it, percent_edit.text.toString().toInt())
                    }
                    val imm = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.hideSoftInputFromWindow(percent_edit.windowToken, 0)
                }
                else -> return@OnEditorActionListener false
            }
            true
        })
    }
}
