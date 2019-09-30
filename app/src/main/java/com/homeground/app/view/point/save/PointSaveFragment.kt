package com.homeground.app.view.point.save


import androidx.fragment.app.Fragment

import com.homeground.app.R
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.databinding.FragmentUserInfoBinding
import com.homeground.app.view.point.save.model.PointSaveViewModel
import com.homeground.app.view.point.search.PointSearchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class PointSaveFragment : BaseFragment<FragmentUserInfoBinding, PointSaveViewModel>() {

    companion object {
        fun newInstance() = PointSaveFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_point_save
    override val vm: PointSaveViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}
