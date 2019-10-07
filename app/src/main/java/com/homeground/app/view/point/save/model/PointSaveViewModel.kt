package com.homeground.app.view.point.save.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.homeground.app.common.base.BaseViewModel
import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.view.point.save.bean.PointInfoListResponseDTO
import com.homeground.app.view.point.save.bean.PointInfoResponseDTO
import com.homeground.app.view.point.search.bean.UserInfoListResponseDTO
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO

class PointSaveViewModel(private val model: PointSaveModel) : BaseViewModel() {
    private val _pointSaveLiveData = MutableLiveData<UserInfoResponseDTO>()
    val pointSaveLiveData: LiveData<UserInfoResponseDTO>
        get() = _pointSaveLiveData

    private val _pointHistoryLiveData = MutableLiveData<PointInfoListResponseDTO>()
    val pointHistoryLiveData: LiveData<PointInfoListResponseDTO>
        get() = _pointHistoryLiveData


    fun savePoint(type:Int, user: UserInfoResponseDTO, point:String){
        model.setPointSave(type, user, point, object : OnResponseListener<UserInfoResponseDTO> {
            override fun onCompleteListener(response: UserInfoResponseDTO) {
                _pointSaveLiveData.value = response
            }
        })
    }

    fun getPointHistory(user: UserInfoResponseDTO){
        model.getPointHistory(user, object : OnResponseListener<PointInfoListResponseDTO> {
            override fun onCompleteListener(response: PointInfoListResponseDTO) {
                _pointHistoryLiveData.value = response
            }
        })
    }
}