package com.homeground.app.view.main

import android.os.Bundle
import com.homeground.app.common.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setReplaceFragment(MainFragment.newInstance())
    }
}
