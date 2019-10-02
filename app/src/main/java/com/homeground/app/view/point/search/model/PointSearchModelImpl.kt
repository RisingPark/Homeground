package com.homeground.app.view.point.search.model

import com.google.firebase.firestore.FirebaseFirestore
import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.model.DataModelImpl
import com.homeground.app.view.point.search.bean.UserInfoListResponseDTO
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import com.orhanobut.logger.Logger

class PointSearchModelImpl : PointSearchModel, DataModelImpl() {
    override fun getUserPoint(
        phoneNum: String?,
        onResponseListener: OnResponseListener<UserInfoListResponseDTO>
    ) {
        FirebaseFirestore.getInstance()
            .collection("user")
            .whereEqualTo("phone_id", phoneNum)
            .get()
            .addOnSuccessListener { documentSnapshot  ->
                Logger.d("[addOnSuccessListener] ${documentSnapshot.toObjects(UserInfoResponseDTO::class.java)}")

                val items = (documentSnapshot.toObjects(UserInfoResponseDTO::class.java) as List<UserInfoResponseDTO>)
                onResponseListener.onCompleteListener(UserInfoListResponseDTO(items).apply {
                    isSuccess = true
                })
            }.addOnFailureListener {
                Logger.d("[addOnFailureListener] ${it.message}")
                val responseDTO = UserInfoListResponseDTO(null).apply {
                    isSuccess = false
                    msg = it.message!!
                }
                onResponseListener.onCompleteListener(responseDTO)
            }
    }
}