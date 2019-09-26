package com.homeground.app.common.base

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.homeground.app.R
import com.orhanobut.logger.Logger
import java.lang.Exception

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        Logger.i("[onCreate] : " + this.javaClass.simpleName)
        setStatusBarColor(R.color.black)
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i("[onDestroy] : " + this.javaClass.simpleName)
    }

    fun setReplaceFragment(fragment:Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.activity_base_container, fragment)
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

    fun getFragmentCount(): Int {
        return supportFragmentManager.backStackEntryCount
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
}
