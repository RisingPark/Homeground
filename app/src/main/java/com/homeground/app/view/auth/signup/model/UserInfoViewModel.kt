package com.homeground.app.view.auth.signup.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.homeground.app.common.base.BaseViewModel
import com.homeground.app.common.bean.BaseResponseDTO
import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.view.main.model.UserInfoModel

class UserInfoViewModel(private val model: UserInfoModel) : BaseViewModel() {
    private val _signUpUserLiveData = MutableLiveData<BaseResponseDTO>()
    val signUpUserLiveData: LiveData<BaseResponseDTO>
        get() = _signUpUserLiveData

    fun setSignUpUser(name: String, phone: String, birthDay:String, note:String) {
        model.signUpUser(name, phone, birthDay, note, object : OnResponseListener<BaseResponseDTO>{
            override fun onCompleteListener(response: BaseResponseDTO) {
                _signUpUserLiveData.value = response
            }
        })
    }
}