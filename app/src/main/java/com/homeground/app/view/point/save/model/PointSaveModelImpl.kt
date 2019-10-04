package com.homeground.app.view.point.save.model

import androidx.core.view.OneShotPreDrawListener.add
import com.google.firebase.firestore.FirebaseFirestore
import com.homeground.app.common.Utils
import com.homeground.app.common.bean.BaseResponseDTO
import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.model.DataModelImpl
import com.homeground.app.view.point.save.PointSaveFragment
import com.homeground.app.view.point.save.bean.PointInfoListResponseDTO
import com.homeground.app.view.point.save.bean.PointInfoResponseDTO
import com.homeground.app.view.point.search.bean.UserInfoListResponseDTO
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import com.orhanobut.logger.Logger
import org.json.JSONObject

class PointSaveModelImpl: PointSaveModel, DataModelImpl() {

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

    private fun getPointList(type: Int, user: UserInfoResponseDTO?, point: Long , onResponseListener: OnResponseListener<UserInfoResponseDTO>?){
        FirebaseFirestore.getInstance()
            .collection("point")
            .document(user?.did!!)
            .get()
            .addOnSuccessListener {
                Logger.d("[addOnSuccessListener] ${it?.data?.get("history")}")

                val history: PointInfoListResponseDTO
                if (it?.data?.get("history") == null){
                    val list = ArrayList<PointInfoResponseDTO>()
                    history = PointInfoListResponseDTO(list)
                } else {

                    //todo 수정
//                    history = (it.data?.get("history"))
                    val list = ArrayList<PointInfoResponseDTO>()
                    history = PointInfoListResponseDTO(list)
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

    private fun addPointList(type: Int, history: PointInfoListResponseDTO , user: UserInfoResponseDTO?, point: Long , onResponseListener: OnResponseListener<UserInfoResponseDTO>?){
        val status = if (type == PointSaveFragment.POINT_SAVE) "+" else "-"

        history.pointInfoResponseDTO.add(PointInfoResponseDTO(user?.did,
            user?.name,
            user?.phone ,
            status,
            user?.last_point_date,
            point))

        val point = hashMapOf(
            "history" to history
        )

        FirebaseFirestore.getInstance()
            .collection("point")
            .document(user?.did!!)
            .set(point)
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

    private fun changePointSave(user: UserInfoResponseDTO?, onResponseListener: OnResponseListener<UserInfoResponseDTO>?){
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