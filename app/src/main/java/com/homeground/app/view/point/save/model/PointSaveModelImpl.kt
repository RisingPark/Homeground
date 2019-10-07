package com.homeground.app.view.point.save.model

import com.google.firebase.firestore.FirebaseFirestore
import com.homeground.app.common.Utils
import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.model.DataModelImpl
import com.homeground.app.view.point.save.PointSaveFragment
import com.homeground.app.view.point.save.bean.PointInfoListResponseDTO
import com.homeground.app.view.point.save.bean.PointInfoResponseDTO
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import com.orhanobut.logger.Logger

class PointSaveModelImpl: PointSaveModel, DataModelImpl() {

    // 포인트 히스토리
    override fun getPointHistory(
        user: UserInfoResponseDTO?,
        onResponseListener: OnResponseListener<PointInfoListResponseDTO>?
    ) {
        FirebaseFirestore.getInstance()
            .collection("point")
            .document(user?.did!!)
            .get()
            .addOnSuccessListener {
                Logger.d("[addOnSuccessListener] ${it}")

                val history: PointInfoListResponseDTO
                history = if (it?.data?.get("pointInfoResponseDTO") == null) {
                    val list = ArrayList<PointInfoResponseDTO>()
                    PointInfoListResponseDTO(list)
                } else {
                    val list = it.data?.get("pointInfoResponseDTO") as ArrayList<PointInfoResponseDTO>
                    PointInfoListResponseDTO(list)
                }

                onResponseListener?.onCompleteListener(history.apply {
                    history.isSuccess = true
                })
            }.addOnFailureListener{
                Logger.d("[addOnFailureListener] ${it.message}")
                val responseDTO = PointInfoListResponseDTO(null).apply {
                    isSuccess = false
                    msg = it.message!!
                }
                onResponseListener?.onCompleteListener(responseDTO)
            }
    }

    override fun setPointSave(type:Int, user: UserInfoResponseDTO?, point:String, onResponseListener: OnResponseListener<UserInfoResponseDTO>?) {
        FirebaseFirestore.getInstance()
            .collection("user")
            .document("${user?.did}")
            .get()
            .addOnSuccessListener { documentSnapshot  ->
                Logger.d("[addOnSuccessListener] ${documentSnapshot.toObject(UserInfoResponseDTO::class.java)}")

                val item = (documentSnapshot.toObject(UserInfoResponseDTO::class.java) as UserInfoResponseDTO)
                item.did = user?.did
                item.point = calPoint(type, item.point, point.toLong()) // 포인트 적립/사용
                item.last_point_date = Utils.getCurrentDate()

                Logger.d("[addOnSuccessListener]item: $item")
                if (item.point!! >= 0){
                    getPointList(type, item, point.toLong() ,onResponseListener)
                } else {
                    val responseDTO = UserInfoResponseDTO(null).apply {
                        isSuccess = false
                        msg = "사용가능 포인트가 부족합니다."
                    }
                    onResponseListener?.onCompleteListener(responseDTO)
                }


            }.addOnFailureListener {
                Logger.d("[addOnFailureListener] ${it.message}")
                val responseDTO = UserInfoResponseDTO(null).apply {
                    isSuccess = false
                    msg = it.message!!
                }
                onResponseListener?.onCompleteListener(responseDTO)
            }
    }

    /**
     * 포인트 리스트 가져오기
     */
    private fun getPointList(type: Int, user: UserInfoResponseDTO?, point: Long , onResponseListener: OnResponseListener<UserInfoResponseDTO>?){
        FirebaseFirestore.getInstance()
            .collection("point")
            .document(user?.did!!)
            .get()
            .addOnSuccessListener {
                Logger.d("[addOnSuccessListener] ${it}")

                val history: PointInfoListResponseDTO
                history = if (it?.data?.get("pointInfoResponseDTO") == null){
                    val list = ArrayList<PointInfoResponseDTO>()
                    PointInfoListResponseDTO(list)
                } else {
                    val list = it.data?.get("pointInfoResponseDTO") as ArrayList<PointInfoResponseDTO>
                    PointInfoListResponseDTO(list)
                }

                addPointList(type, history, user, point, onResponseListener)
            }.addOnFailureListener{
                Logger.d("[addOnFailureListener] ${it.message}")
                val responseDTO = UserInfoResponseDTO(null).apply {
                    isSuccess = false
                    msg = it.message!!
                }
                onResponseListener?.onCompleteListener(responseDTO)
            }

    }

    /**
     * 포인트 리스트에 추가
     */
    private fun addPointList(type: Int, history: PointInfoListResponseDTO? , user: UserInfoResponseDTO?, point: Long , onResponseListener: OnResponseListener<UserInfoResponseDTO>?){
        val status = if (type == PointSaveFragment.POINT_SAVE) "+" else "-"

        history?.pointInfoResponseDTO?.add(PointInfoResponseDTO(user?.did,
            user?.name,
            user?.phone ,
            status,
            user?.last_point_date,
            point))

        history?.let {
            FirebaseFirestore.getInstance()
                .collection("point")
                .document(user?.did!!)
                .set(it)
                .addOnSuccessListener {
                    Logger.d("[addOnSuccessListener]")
                    changePointSave(user, onResponseListener)
                }.addOnFailureListener{
                    Logger.d("[addOnFailureListener] ${it.message}")
                    val responseDTO = UserInfoResponseDTO(null).apply {
                        isSuccess = false
                        msg = it.message!!
                    }
                    onResponseListener?.onCompleteListener(responseDTO)
                }
        }
    }

    private fun changePointSave(user: UserInfoResponseDTO?, onResponseListener: OnResponseListener<UserInfoResponseDTO>?) {
        FirebaseFirestore.getInstance()
            .collection("user")
            .document("${user?.did}")
            .set(user!!)
            .addOnSuccessListener {
                onResponseListener?.onCompleteListener(user.apply {
                    Logger.d("[addOnSuccessListener]")
                    isSuccess = true
                })
            }.addOnFailureListener {
                Logger.d("[addOnFailureListener] ${it.message}")
                val responseDTO = UserInfoResponseDTO(null).apply {
                    isSuccess = false
                    msg = it.message!!
                }
                onResponseListener?.onCompleteListener(responseDTO)
            }
    }

    private fun calPoint(type:Int, totalPoint:Long?, point:Long) : Long{
        var resultPoint: Long = 0
        when(type){
            PointSaveFragment.POINT_SAVE -> {
                resultPoint = totalPoint?.plus(point)!!
            }
            PointSaveFragment.POINT_USE -> {
                resultPoint = totalPoint?.minus(point)!!
            }
        }
        return resultPoint
    }
}