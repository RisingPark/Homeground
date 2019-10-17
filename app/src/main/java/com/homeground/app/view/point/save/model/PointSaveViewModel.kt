package com.homeground.app.view.point.save.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.homeground.app.common.base.BaseViewModel
import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.view.point.save.bean.PointInfoListResponseDTO
import com.homeground.app.view.point.save.bean.PointInfoResponseDTO
import com.homeground.app.view.point.search.bean.UserInfoListResponseDTO
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import com.orhanobut.logger.Logger

class PointSaveViewModel(private val model: PointSaveModel) : BaseViewModel() {
    private val _pointSaveLiveData = MutableLiveData<UserInfoResponseDTO>()
    val pointSaveLiveData: LiveData<UserInfoResponseDTO>
        get() = _pointSaveLiveData

    private val _pointHistoryLiveData = MutableLiveData<PointInfoListResponseDTO>()
    val pointHistoryLiveData: LiveData<PointInfoListResponseDTO>
        get() = _pointHistoryLiveData

    private val _cancelPointHistoryLiveData = MutableLiveData<PointInfoListResponseDTO>()
    val cancelPointHistoryLiveData: LiveData<PointInfoListResponseDTO>
        get() = _cancelPointHistoryLiveData

    /**
     * 포인트 적립
     */
    fun savePoint(type:Int, user: UserInfoResponseDTO, device: String, point:String){
        model.setPointSave(type, user, device ,point, object : OnResponseListener<UserInfoResponseDTO> {
            override fun onCompleteListener(response: UserInfoResponseDTO) {
                _pointSaveLiveData.value = response
            }
        }, object : OnResponseListener<PointInfoListResponseDTO> {
            override fun onCompleteListener(response: PointInfoListResponseDTO) {
                _pointHistoryLiveData.value = response
            }
        })
    }

    /**
     * 포인트 내역
     */
    fun getPointHistory(user: UserInfoResponseDTO){
        model.getPointHistory(user, object : OnResponseListener<PointInfoListResponseDTO> {
            override fun onCompleteListener(response: PointInfoListResponseDTO) {
                _pointHistoryLiveData.value = response
            }
        })
    }

    fun cancelPoint(user: UserInfoResponseDTO, pointList: ArrayList<PointInfoResponseDTO>?, position: Int){
        model.updatePointHistory(user, pointList, position, object :OnResponseListener<UserInfoResponseDTO>{
            override fun onCompleteListener(response: UserInfoResponseDTO) {
                _pointSaveLiveData.value = response
            }

        },object : OnResponseListener<PointInfoListResponseDTO> {
            override fun onCompleteListener(response: PointInfoListResponseDTO) {
                _pointHistoryLiveData.value = response
            }
        })
    }
}