package com.homeground.app.view.point.search.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.homeground.app.common.base.BaseViewModel
import com.homeground.app.view.point.search.model.PointSearchModel

class PointSearchViewModel(private val model: PointSearchModel) : BaseViewModel() {
    private val _tempLiveData = MutableLiveData<String>()
    val tempLiveData: LiveData<String>
        get() = _tempLiveData

}