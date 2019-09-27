package com.homeground.app.view.atemplate


import androidx.fragment.app.Fragment

import com.homeground.app.R
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.databinding.FragmentUserInfoBinding
import com.homeground.app.view.main.model.TemplateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class TemplateFragment : BaseFragment<FragmentUserInfoBinding, TemplateViewModel>() {

    companion object {
        fun newInstance() = TemplateFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_user_info
    override val vm: TemplateViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}
