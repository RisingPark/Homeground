package com.homeground.app.view.auth.signup.model

import com.homeground.app.common.bean.BaseResponseDTO
import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import com.tistory.deque.kotlinmvvmsample.model.DataModel

interface UserInfoModel :DataModel {

    fun signUpUser(
        name: String,
        phone: String,
        birthDay: String,
        note: String,
        onResponseListener: OnResponseListener<BaseResponseDTO>
    )

    fun modifyUser(
        user: UserInfoResponseDTO?,
        name: String,
        phone: String,
        birthDay: String,
        note: String,
        onResponseListener: OnResponseListener<UserInfoResponseDTO>
    )
}