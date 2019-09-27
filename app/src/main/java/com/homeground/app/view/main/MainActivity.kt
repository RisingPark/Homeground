package com.homeground.app.view.main

import android.os.Bundle
import com.homeground.app.common.base.BaseActivity
import com.orhanobut.logger.Logger
import java.util.logging.LogManager

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setReplaceFragment(MainFragment.newInstance())
    }

    override fun onBackPressed() {
        if (getCurrentFragment() is MainFragment) {
            val mainFragment = getCurrentFragment() as MainFragment
            if (mainFragment.isDrawerOpen()){
                mainFragment.closeDrawer()
            } else {
                super.onBackPressed()
            }
        } else{
            super.onBackPressed()
        }
    }
}
