package com.homeground.app.view.main


import android.os.Bundle
import android.view.Gravity
import androidx.core.view.GravityCompat
import androidx.databinding.adapters.ViewBindingAdapter.setClickListener
import androidx.fragment.app.Fragment
import com.homeground.app.R
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.databinding.FragmentMainBinding
import com.homeground.app.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.fragment_main
    override val viewModel: MainViewModel by viewModel()

    override fun initStartView() {
        setClickListener()
        setDrawerLayout()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }


    private fun setClickListener() {
        menu.setOnClickListener {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)){
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }

    }
    private fun setDrawerLayout() {

    }
}
