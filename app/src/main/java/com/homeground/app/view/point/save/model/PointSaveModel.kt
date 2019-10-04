package com.homeground.app.view.point.save.model

import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.view.point.search.bean.UserInfoListResponseDTO
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import com.tistory.deque.kotlinmvvmsample.model.DataModel

interface PointSaveModel :DataModel {

    fun setPointSave(type:Int, user: UserInfoResponseDTO?, point:String, onResponseListener: OnResponseListener<UserInfoResponseDTO>?)
}