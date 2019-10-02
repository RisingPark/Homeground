package com.homeground.app.view.point.search.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.homeground.app.common.base.BaseViewModel
import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.view.point.search.bean.UserInfoListResponseDTO
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import com.homeground.app.view.point.search.model.PointSearchModel
import com.orhanobut.logger.Logger

class PointSearchViewModel(private val model: PointSearchModel) : BaseViewModel() {
    private val _userPointLiveData = MutableLiveData<UserInfoListResponseDTO>()
    val userPointLiveData: LiveData<UserInfoListResponseDTO>
        get() = _userPointLiveData

    // 유저 포인트 조회
    fun getUserPoint(phoneNum: String?){
        model.getUserPoint(phoneNum, object :OnResponseListener<UserInfoListResponseDTO>{
            override fun onCompleteListener(response: UserInfoListResponseDTO) {
                _userPointLiveData.value = response
            }
        })
    }
}