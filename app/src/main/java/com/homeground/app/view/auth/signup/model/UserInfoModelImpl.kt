package com.homeground.app.view.auth.signup.model

import com.google.firebase.firestore.FirebaseFirestore
import com.homeground.app.common.Utils
import com.homeground.app.common.bean.BaseResponseDTO
import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.model.DataModelImpl
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import com.orhanobut.logger.Logger

class UserInfoModelImpl: UserInfoModel, DataModelImpl() {
    override fun modifyUser(
        user: UserInfoResponseDTO?,
        name: String,
        phone: String,
        birthDay: String,
        note: String,
        onResponseListener: OnResponseListener<UserInfoResponseDTO>
    ) {
        val phoneId =  phone.split("-")[2]
        val userMap = hashMapOf(
            "name" to name,
            "phone" to phone,
            "phone_id" to phoneId,
            "birthday" to birthDay,
            "note" to note
        )
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(user?.did.toString())
            .update(userMap as Map<String, Any>)
            .addOnSuccessListener {
                Logger.d("[addOnSuccessListener]")

                onResponseListener.onCompleteListener(user?.apply {
                    isSuccess = true
                    this.name = name
                    this.phone = phone
                    this.phone_id = phoneId
                    this.birthday = birthDay
                    this.note = note
                }!!)
            }.addOnFailureListener{
                Logger.d("[addOnFailureListener] ${it.message}")
                onResponseListener.onCompleteListener(UserInfoResponseDTO().apply {
                    isSuccess = false
                    msg = ""
                })
            }
    }

    override fun signUpUser(
        name: String,
        phone: String,
        birthDay: String,
        note: String,
        onResponseListener: OnResponseListener<BaseResponseDTO>
    ) {
        val lastDate = Utils.getCurrentDate()
        val phoneId =  phone.split("-")[2]
        val user = hashMapOf(
            "name" to name,
            "phone" to phone,
            "phone_id" to phoneId,
            "birthday" to birthDay,
            "note" to note,
            "sign_up_date" to lastDate,
            "point" to 0,
            "last_point_date" to lastDate
        )
        FirebaseFirestore.getInstance()
            .collection("user")
            .add(user)
            .addOnSuccessListener {
                Logger.d("[addOnSuccessListener]")
                onResponseListener.onCompleteListener(BaseResponseDTO(true, ""))
            }.addOnFailureListener{
                Logger.d("[addOnFailureListener] ${it.message}")
                onResponseListener.onCompleteListener(BaseResponseDTO(false, ""))
            }
    }

}