package com.homeground.app.view.point.save.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.homeground.app.common.base.BaseViewModel
import com.homeground.app.view.point.save.model.PointSaveModel

class PointSaveViewModel(private val model: PointSaveModel) : BaseViewModel() {
    private val _tempLiveData = MutableLiveData<String>()
    val tempLiveData: LiveData<String>
        get() = _tempLiveData

}