package com.homeground.app.common.base

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.homeground.app.HApplication
import com.homeground.app.R
import com.orhanobut.logger.Logger
import java.lang.Exception

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        Logger.i("[onCreate] : " + this.javaClass.simpleName)

        (application as HApplication).activityList.add(this)
        setStatusBarColor(R.color.black)
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i("[onDestroy] : " + this.javaClass.simpleName)
        (application as HApplication).activityList.remove(this)
    }

    fun setReplaceFragment(fragment:Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.activity_base_container, fragment, "0")
            commitAllowingStateLoss()
        }
    }

    override fun onBackPressed() {
        try{
            if (getFragmentCount() == 0) {
                finish()
            } else {
                supportFragmentManager.popBackStack()
            }
        } catch (e : Exception){
            super.onBackPressed()
        }
    }

    fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            // 마시멜로부터 Status Bar Color 지정
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, color)
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            // 롤리팝 일때는 Status Bar Color 검정으로 설정
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)
        }
    }

    /**
     * Current Fragment 가져오기
     *
     * @return Current Fragment
     */
    fun getCurrentFragment(): Fragment? {
        return getFragmentAt(getFragmentCount())
    }

    fun getFragmentCount(): Int {
        return supportFragmentManager.backStackEntryCount
    }

    fun getFragmentAt(index: Int): Fragment? {
        return supportFragmentManager.findFragmentByTag(index.toString())
    }

    /**
     * Add Fragment
     *
     * @param fragment
     */
    fun addFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(
            R.anim.slide_in_from_right,
            0,
            0,
             R.anim.slide_out_to_right
        )
        ft.add(R.id.activity_base_container, fragment, Integer.toString(getFragmentCount() + 1))
        ft.addToBackStack(null)
        ft.commitAllowingStateLoss()
    }

    /**
     * Replace Fragment
     *
     * @param fragment
     */
    fun replaceFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(
            R.anim.slide_in_from_right,
            0,
            0,
             R.anim.slide_out_to_right
        )
        ft.replace(R.id.activity_base_container, fragment, Integer.toString(getFragmentCount() + 1))
        ft.addToBackStack(null)
        ft.commitAllowingStateLoss()
    }


}
