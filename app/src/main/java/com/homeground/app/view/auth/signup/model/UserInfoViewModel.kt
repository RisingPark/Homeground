package com.homeground.app.view.main.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.homeground.app.common.base.BaseViewModel

class UserInfoViewModel(private val model: UserInfoModel) : BaseViewModel() {
    private val _tempLiveData = MutableLiveData<String>()
    val tempLiveData: LiveData<String>
        get() = _tempLiveData

}