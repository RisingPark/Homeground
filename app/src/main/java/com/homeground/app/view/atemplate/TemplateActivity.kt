package com.homeground.app.view.atemplate

import android.os.Bundle
import com.homeground.app.R
import com.homeground.app.common.base.BaseActivity
import com.homeground.app.view.auth.signup.UserInfoFragment

class TemplateActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setReplaceFragment(TemplateFragment.newInstance())
    }
}
