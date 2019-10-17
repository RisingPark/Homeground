package com.homeground.app.view.main


import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.homeground.app.BuildConfig
import com.homeground.app.R
import com.homeground.app.common.CommonOpener
import com.homeground.app.common.Preference
import com.homeground.app.common.base.BaseFragment
import com.homeground.app.databinding.FragmentMainBinding
import com.homeground.app.view.main.adapter.MenuRecyclerViewAdapter
import com.homeground.app.common.interfaces.OnItemClickListener
import com.homeground.app.common.ui.banner.GlideImageLoader
import com.homeground.app.view.main.bean.MenuItemDTO
import com.homeground.app.view.main.model.MainViewModel
import com.youth.banner.BannerConfig
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
        checkFirstLauncher()
        if (!BuildConfig.DEBUG)
            setBanner()
    }

    override fun initDataBinding() {
        vm.menuItemLiveData.observe(this, Observer {
            setDrawerLayout(it)
        })
        vm.setMenuItems(context)
    }

    override fun initAfterBinding() {
    }


    override fun onStart() {
        super.onStart()
        if (isAdded)
            banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        if (isAdded)
            banner.stopAutoPlay()
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

    private fun setBanner(){
        val imageList = ArrayList<String>()
        imageList.add("https://scontent-icn1-1.xx.fbcdn.net/v/t1.0-9/11873360_898280743552396_101394436020645043_n.jpg?_nc_cat=107&_nc_oc=AQmC93feHG01xrjlPKLc5dSGCzdR7KtkPSLTl_XXOPo2r5UUxXrnCA0ZxGyPRcXGtLY&_nc_ht=scontent-icn1-1.xx&oh=9f2ddefe04b8cd85b44841e5c8ab5f0b&oe=5E32A245")
        imageList.add("https://scontent-icn1-1.xx.fbcdn.net/v/t1.0-9/11916097_900336123346858_2967648002865893854_n.jpg?_nc_cat=100&_nc_oc=AQkMZu_QIQsFZ3eXnXYGxOpYZebMB5bpoV9U3b9CB722SKwKFyKtsZShmxSvNThETJU&_nc_ht=scontent-icn1-1.xx&oh=b1544fa35d553801b4c1661c83a97e02&oe=5E1F65D6")
        imageList.add("https://scontent-icn1-1.xx.fbcdn.net/v/t1.0-9/11891200_900336073346863_4264895089687910919_n.jpg?_nc_cat=108&_nc_oc=AQn9h1InZwt97XWipooSazbtwNWV79lqNhNfrFmDYP5cueA43tyYi8mTbBTTfr6ud5Q&_nc_ht=scontent-icn1-1.xx&oh=ec0793bea88f91f51f3104e28d31de44&oe=5E246685")
        imageList.add("https://scontent-icn1-1.xx.fbcdn.net/v/t1.0-9/11898839_900335976680206_4599340270674704067_n.jpg?_nc_cat=103&_nc_oc=AQmD9MblM1rQLQ39OaLRq8Ysbjfvb5YiiX1nlCEO26d-5uJ0n_g8ZzzUjl6O9jqWZVg&_nc_ht=scontent-icn1-1.xx&oh=feb7c39f5ce4bc95e3a985d100947f04&oe=5E34CA52")
        banner.setImageLoader(GlideImageLoader())
        banner.setImages(imageList)
        banner.isAutoPlay(true)
        banner.setDelayTime(7000)
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner.start()
    }


    private fun checkFirstLauncher(){
        activity?.let {
            if (Preference.isFirstLauncher(it)) {
                Preference.setDeviceName(it,"POS_"+(0..99).random())
                Preference.setFirstLauncher(it, false)
            }
        }
    }
}
