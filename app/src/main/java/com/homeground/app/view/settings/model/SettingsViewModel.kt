package com.homeground.app.view.settings.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.homeground.app.common.base.BaseViewModel

class SettingsViewModel(private val model: SettingsViewModel) : BaseViewModel() {
    private val _tempLiveData = MutableLiveData<String>()
    val tempLiveData: LiveData<String>
        get() = _tempLiveData

}