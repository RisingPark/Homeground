package com.homeground.app.view.point.save.model

import android.content.Context
import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.view.point.save.bean.PointInfoListResponseDTO
import com.homeground.app.view.point.save.bean.PointInfoResponseDTO
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import com.tistory.deque.kotlinmvvmsample.model.DataModel

interface PointSaveModel :DataModel {

    fun setPointSave(context: Context, type:Int, user: UserInfoResponseDTO?, device: String, point:String, onResponseListener: OnResponseListener<UserInfoResponseDTO>?, onPointListResponseListener: OnResponseListener<PointInfoListResponseDTO>?)
    fun getPointHistory(user: UserInfoResponseDTO?, onResponseListener: OnResponseListener<PointInfoListResponseDTO>?)
    fun updatePointHistory(context: Context, user: UserInfoResponseDTO?, pointList: ArrayList<PointInfoResponseDTO>?, position: Int, onResponseListener: OnResponseListener<UserInfoResponseDTO>? ,onPointResponseListener: OnResponseListener<PointInfoListResponseDTO>?)
}