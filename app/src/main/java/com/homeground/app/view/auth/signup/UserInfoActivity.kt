package com.homeground.app.view.auth.signup

import android.os.Bundle
import com.homeground.app.R
import com.homeground.app.common.base.BaseActivity

class UserInfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setReplaceFragment(UserInfoFragment.newInstance(UserInfoFragment.SIGN_UP))
    }
}
