package com.homeground.app.view.main.model

import com.homeground.app.common.bean.BaseResponseDTO
import com.homeground.app.common.interfaces.OnResponseListener
import com.tistory.deque.kotlinmvvmsample.model.DataModel

interface UserInfoModel :DataModel {

    fun signUpUser(
        name: String,
        phone: String,
        birthDay: String,
        note: String,
        onResponseListener: OnResponseListener<BaseResponseDTO>
    )
}