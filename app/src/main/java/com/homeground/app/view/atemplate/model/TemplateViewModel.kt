package com.homeground.app.view.main.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.homeground.app.common.base.BaseViewModel

class TemplateViewModel(private val model: TemplateModel) : BaseViewModel() {
    private val _tempLiveData = MutableLiveData<String>()
    val tempLiveData: LiveData<String>
        get() = _tempLiveData

}