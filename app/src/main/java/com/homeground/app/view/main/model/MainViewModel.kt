package com.homeground.app.view.main.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.homeground.app.common.base.BaseViewModel
import com.homeground.app.view.main.bean.MenuItemDTO

class MainViewModel(private val model: MainModel) : BaseViewModel() {
    private val _menuItemLiveData = MutableLiveData<ArrayList<MenuItemDTO>>()
    val menuItemLiveData: LiveData<ArrayList<MenuItemDTO>>
        get() = _menuItemLiveData

    fun setMenuItems(context: Context?){
        _menuItemLiveData.postValue(model.getRecyclerViewData(context))
    }
}