package com.homeground.app.view.point.search


import androidx.fragment.app.Fragment

import com.homeground.app.R
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.databinding.FragmentUserInfoBinding
import com.homeground.app.view.point.search.model.PointSearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class PointSearchFragment : BaseFragment<FragmentUserInfoBinding, PointSearchViewModel>() {

    companion object {
        fun newInstance() = PointSearchFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_point_search
    override val vm : PointSearchViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}
