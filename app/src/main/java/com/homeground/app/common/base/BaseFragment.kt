package com.homeground.app.common.base

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.orhanobut.logger.Logger

/**
 * A simple [Fragment] subclass.
 *
 */
abstract class BaseFragment<T : ViewDataBinding, R : BaseViewModel> : Fragment() {

    lateinit var viewDataBinding: T
    abstract val layoutResourceId: Int
    abstract val vm: R
    val mHandler = Handler()

    abstract fun initStartView()
    abstract fun initDataBinding()
    abstract fun initAfterBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Logger.i("[onActivityCreated] : " + this.javaClass.simpleName)
        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i("[onDestroy] : " + this.javaClass.simpleName)
    }

    fun addFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            add(com.homeground.app.R.id.activity_base_container, fragment, fragment.javaClass.simpleName)
            addToBackStack(null)
            commitAllowingStateLoss()
        }
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    fun showLoadingProgress(){
        getBaseActivity()?.showLoadingProgressBar()
    }

    fun hideLoadingProgress(){
        getBaseActivity()?.hideLoadingProgressBar()
    }

    fun getBaseActivity() : BaseActivity? {
        return activity as BaseActivity
    }
}
