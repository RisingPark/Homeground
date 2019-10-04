package com.homeground.app.view.point.search

import android.os.Bundle
import com.homeground.app.R
import com.homeground.app.common.base.BaseActivity
import com.homeground.app.view.point.save.PointSaveFragment
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO

class PointSearchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setReplaceFragment(PointSearchFragment.newInstance())
    }

    fun changeUserInfo(user: UserInfoResponseDTO?){
        if (getFragmentAt(getFragmentCount()-1) is PointSearchFragment)
            (getFragmentAt(getFragmentCount()-1) as PointSearchFragment).changeUserData(user)
    }
}
