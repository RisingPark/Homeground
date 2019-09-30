package com.homeground.app.view.settings


import androidx.fragment.app.Fragment

import com.homeground.app.R
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.databinding.FragmentUserInfoBinding
import com.homeground.app.view.settings.model.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}
