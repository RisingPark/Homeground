package com.homeground.app.view.auth.signup.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.homeground.app.common.base.BaseViewModel
import com.homeground.app.common.bean.BaseResponseDTO
import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO

class UserInfoViewModel(private val model: UserInfoModel) : BaseViewModel() {
    private val _signUpUserLiveData = MutableLiveData<BaseResponseDTO>()
    val signUpUserLiveData: LiveData<BaseResponseDTO>
        get() = _signUpUserLiveData

    private val _modifyUserLiveData = MutableLiveData<UserInfoResponseDTO>()
    val modifyUserLiveData: LiveData<UserInfoResponseDTO>
        get() = _modifyUserLiveData

    fun setSignUpUser(name: String, phone: String, birthDay:String, note:String) {
        model.signUpUser(name, phone, birthDay, note, object : OnResponseListener<BaseResponseDTO>{
            override fun onCompleteListener(response: BaseResponseDTO) {
                _signUpUserLiveData.value = response
            }
        })
    }

    fun setModifyUser(user: UserInfoResponseDTO, name: String, phone: String, birthDay:String, note:String) {
        model.modifyUser(user, name, phone, birthDay, note, object : OnResponseListener<UserInfoResponseDTO>{
            override fun onCompleteListener(response: UserInfoResponseDTO) {
                _modifyUserLiveData.value = response
            }
        })
    }
}