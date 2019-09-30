package com.homeground.app.view.main.model

import com.google.firebase.firestore.FirebaseFirestore
import com.homeground.app.common.bean.BaseResponseDTO
import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.model.DataModelImpl
import com.orhanobut.logger.Logger
import java.util.*

class UserInfoModelImpl: UserInfoModel, DataModelImpl() {

    override fun signUpUser(
        name: String,
        phone: String,
        birthDay: String,
        note: String,
        onResponseListener: OnResponseListener<BaseResponseDTO>
    ) {

        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH)
        var lastDate = "$year-${month+1}-$dayOfMonth"

        val user = hashMapOf(
            "name" to name,
            "phone" to phone,
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
                onResponseListener.onSuccessListener(BaseResponseDTO(true, ""))
            }.addOnFailureListener{
                Logger.d("[addOnFailureListener] ${it.message}")
                onResponseListener.onFailureListener(BaseResponseDTO(false, ""))
            }
    }

}