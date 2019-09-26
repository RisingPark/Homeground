package com.homeground.app.view.intro

import android.os.Bundle
import com.homeground.app.R
import com.homeground.app.common.base.BaseActivity

class IntroActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setReplaceFragment(IntroFragment.newInstance())
    }
}
