package com.homeground.app.view.main.model

import android.content.Context
import com.homeground.app.view.main.bean.MenuItemDTO
import com.tistory.deque.kotlinmvvmsample.model.DataModel

interface MainModel :DataModel {

    fun getRecyclerViewData(context: Context?): ArrayList<MenuItemDTO>
}