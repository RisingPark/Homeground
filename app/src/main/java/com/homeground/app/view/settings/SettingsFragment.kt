package com.homeground.app.view.settings

import android.view.View
import androidx.fragment.app.Fragment
import com.homeground.app.BuildConfig
import com.homeground.app.common.Utils
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.databinding.FragmentUserInfoBinding
import com.homeground.app.view.settings.model.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.homeground.app.R
import com.homeground.app.common.DialogHelper
import com.homeground.app.common.Preference
import com.homeground.app.common.base.BaseActivity

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : BaseFragment<FragmentUserInfoBinding, SettingsViewModel>() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_settings
    override val vm: SettingsViewModel by viewModel()

    override fun initStartView() {
        setToolbar()
        setVersion()
        setSettingsValue()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
    }


    private fun setToolbar() {
        common_toolbar_title_text.text = getString(R.string.settings)
        common_toolbar_back_image.setOnClickListener {
            activity?.onBackPressed()
        }

        common_toolbar_right_text.visibility = View.VISIBLE
        common_toolbar_right_text.text = getString(R.string.change)
        common_toolbar_right_text.setOnClickListener {
            activity?.let { it1 ->
                Preference.setPercent(it1, percent_edit.text.toString().toInt())
                Preference.setDeviceName(it1, device_name_edit.text.toString())
                DialogHelper.showCommonDialog(activity as BaseActivity?, getString(R.string.change_finish), null)
            }
        }
    }

    private fun setVersion(){
        settings_current_version.text = activity?.let { Utils.getApplicationVersionName(it, BuildConfig.VERSION_NAME) }
    }

    private fun setSettingsValue(){
        activity?.let { it ->
            percent_edit.setText(Preference.getPercent(it).toString())
            device_name_edit.setText(Preference.getDeviceName(it))
        }
    }
}
