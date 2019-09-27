package com.homeground.app.view.main.model

import android.content.Context
import com.homeground.app.R
import com.homeground.app.model.DataModelImpl
import com.homeground.app.view.auth.signup.UserInfoActivity
import com.homeground.app.view.main.bean.MenuItemDTO
import com.homeground.app.view.point.save.PointSaveActivity
import com.homeground.app.view.point.search.PointSearchActivity
import com.homeground.app.view.settings.SettingsActivity

class MainModelImpl: MainModel, DataModelImpl() {

    override fun getRecyclerViewData(context: Context?): ArrayList<MenuItemDTO> {
        val defaultItem = ArrayList<MenuItemDTO>()
        defaultItem.add(MenuItemDTO().apply {
            title = context?.getString(R.string.sign_up)!!
            iconRes = R.drawable.baseline_person_add_black_36
            cls = UserInfoActivity::class.java
        })

        defaultItem.add(MenuItemDTO().apply {
            title = context?.getString(R.string.save)!!
            iconRes = R.drawable.baseline_local_parking_black_36
            cls = PointSaveActivity::class.java
        })

        defaultItem.add(MenuItemDTO().apply {
            title = context?.getString(R.string.search)!!
            iconRes = R.drawable.baseline_search_black_36
            cls = PointSearchActivity::class.java
        })

        defaultItem.add(MenuItemDTO().apply {
            title = context?.getString(R.string.settings)!!
            iconRes = R.drawable.outline_settings_applications_black_36
            cls = SettingsActivity::class.java
        })

        return defaultItem
    }
}