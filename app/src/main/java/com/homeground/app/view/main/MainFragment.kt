package com.homeground.app.view.main


import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.homeground.app.R
import com.homeground.app.common.CommonOpener
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.databinding.FragmentMainBinding
import com.homeground.app.view.main.adapter.MenuRecyclerViewAdapter
import com.homeground.app.common.interfaces.OnItemClickListener
import com.homeground.app.view.main.bean.MenuItemDTO
import com.homeground.app.view.main.model.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_left_menu.*
import org.koin.android.ext.android.inject
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
    override val vm: MainViewModel by viewModel()
    private val mMenuRecyclerViewAdapter: MenuRecyclerViewAdapter by inject()

    override fun initStartView() {
        setClickListener()
    }

    override fun initDataBinding() {
        vm.menuItemLiveData.observe(this, Observer {
            setDrawerLayout(it)
        })
        vm.setMenuItems(context)
    }

    override fun initAfterBinding() {
    }


    private fun setClickListener() {
        menu.setOnClickListener {
            if (!drawer_layout.isDrawerOpen(GravityCompat.START)){
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }

    }

    private fun setDrawerLayout(items: ArrayList<MenuItemDTO>) {
        menu_recycler_view.run {
            adapter = mMenuRecyclerViewAdapter.apply {
                this.items = items
                onItemClickListener = object : OnItemClickListener{
                    override fun onItemClickListener(view: View, position: Int) {
                        activity?.let { CommonOpener.openAnyActivity(it, items[position].cls) }
                    }
                }
            }
        }
    }

    fun isDrawerOpen(): Boolean{
        return drawer_layout.isDrawerOpen(GravityCompat.START)
    }

    fun closeDrawer(){
        drawer_layout.closeDrawer(GravityCompat.START)
    }
}
