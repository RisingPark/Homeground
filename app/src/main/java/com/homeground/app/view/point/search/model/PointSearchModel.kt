package com.homeground.app.view.point.search.model

import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.view.point.search.bean.UserInfoListResponseDTO
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import com.tistory.deque.kotlinmvvmsample.model.DataModel

interface PointSearchModel :DataModel {

    fun getUserPoint(phoneNum: String?, onResponseListener: OnResponseListener<UserInfoListResponseDTO>)
}