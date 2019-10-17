package com.homeground.app.view.point.save.model

import com.google.firebase.firestore.FirebaseFirestore
import com.homeground.app.common.Utils
import com.homeground.app.common.interfaces.OnResponseListener
import com.homeground.app.model.DataModelImpl
import com.homeground.app.view.point.save.PointSaveFragment
import com.homeground.app.view.point.save.adapter.PointHistoryAdapter
import com.homeground.app.view.point.save.bean.PointInfoListResponseDTO
import com.homeground.app.view.point.save.bean.PointInfoResponseDTO
import com.homeground.app.view.point.search.bean.UserInfoResponseDTO
import com.orhanobut.logger.Logger
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

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

    /**
     * 포인트 적립
     */
    override fun setPointSave(
        type: Int,
        user: UserInfoResponseDTO?,
        device: String,
        point: String,
        onResponseListener: OnResponseListener<UserInfoResponseDTO>?,
        onPointListResponseListener: OnResponseListener<PointInfoListResponseDTO>?
    ) {
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
                    getPointList(type, item, device, point.toLong() ,onResponseListener, onPointListResponseListener)
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
    private fun getPointList(type: Int, user: UserInfoResponseDTO?, device: String, point: Long , onResponseListener: OnResponseListener<UserInfoResponseDTO>?, onPointListResponseListener: OnResponseListener<PointInfoListResponseDTO>?){
        FirebaseFirestore.getInstance()
            .collection("point")
            .document(user?.did!!)
            .get()
            .addOnSuccessListener {
                Logger.d("[addOnSuccessListener] ${it}")

                val history: PointInfoListResponseDTO?
                history = if (it?.data?.get("pointInfoResponseDTO") == null){
                    val list = ArrayList<PointInfoResponseDTO>()
                    PointInfoListResponseDTO(list)
                } else {
                    val list = it.data?.get("pointInfoResponseDTO") as ArrayList<PointInfoResponseDTO>
                    PointInfoListResponseDTO(list)
                }

                addPointList(type, history, user, device, point, onResponseListener, onPointListResponseListener)
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
    private fun addPointList(type: Int, history: PointInfoListResponseDTO?, user: UserInfoResponseDTO?, device: String, point: Long , onResponseListener: OnResponseListener<UserInfoResponseDTO>?, onPointListResponseListener: OnResponseListener<PointInfoListResponseDTO>?){
        val status = if (type == PointSaveFragment.POINT_SAVE) "+" else "-"

        history?.pointInfoResponseDTO?.add(PointInfoResponseDTO(user?.did,
            device,
            user?.name,
            user?.phone,
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
                    onPointListResponseListener?.onCompleteListener(history.apply {
                        isSuccess = true
                    })
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

    /**
     * 포인트 계산
     */
    private fun calPoint(type:Int, totalPoint:Long?, point:Long) : Long {
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

    /**
     * 포인트 히스토리 변경
     */
    override fun updatePointHistory(
        user: UserInfoResponseDTO?,
        pointList: ArrayList<PointInfoResponseDTO>?,
        position: Int,
        onResponseListener: OnResponseListener<UserInfoResponseDTO>?,
        onPointResponseListener: OnResponseListener<PointInfoListResponseDTO>?
    ) {

        FirebaseFirestore.getInstance()
            .collection("point")
            .document(user?.did!!)
            .get()
            .addOnSuccessListener {
                Logger.d("[addOnSuccessListener] ${it}")

                val list = it.data?.get("pointInfoResponseDTO") as ArrayList<*>?

                var isValid = false
                val isHashMap = list?.toArray()?.get(position) is HashMap<*,*>
                if (list?.size == pointList?.size) {
                    for (i in list?.indices!!){
                        if (list?.toArray()?.get(i) is HashMap<*,*>) {
                            val currentMap = parseMap(list, i)

                            var state= ""
                            try{
                                state = parseMap(pointList, i).get("state") as String
                            } catch (e:Exception){
                                state = parseList(pointList, i).state.toString()
                            }

                            isValid = currentMap?.get("state").toString().equals(state)
                            if (!isValid) break
                        }

                    }
                    if (isValid){
                        val cancelPoint = parseMap(list, position)?.get("point").toString().toLong()
                        when(parseMap(list, position)?.get("state")) {
                            "+" -> {
                                user?.point = user?.point?.minus(cancelPoint)
                            }
                            "-" -> {
                                user?.point = user?.point?.plus(cancelPoint)
                            }
                        }

                        parseMap(list, position)["state"] = "!${parseMap(list, position)?.get("state")}"
                        changePointList(user, PointInfoListResponseDTO(list as ArrayList<PointInfoResponseDTO>?), onResponseListener, onPointResponseListener)
                    } else{
                        onResponseListener?.onCompleteListener(UserInfoResponseDTO().apply {
                            isSuccess = false
                            msg = "취소 중 에러발생: 회원 조회를 다시 해주시기 바랍니다."
                        })
                    }
                } else {
                    onResponseListener?.onCompleteListener(UserInfoResponseDTO().apply {
                        isSuccess = false
                        msg = "취소 중 에러발생: 회원 조회를 다시 해주시기 바랍니다."
                    })
                }


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
     * 포인트 내역 변경
     */
    private fun changePointList(
        user: UserInfoResponseDTO?,
        history: PointInfoListResponseDTO?,
        onResponseListener: OnResponseListener<UserInfoResponseDTO>?,
        onPointListResponseListener: OnResponseListener<PointInfoListResponseDTO>?)
    {
        history?.let {
            FirebaseFirestore.getInstance()
                .collection("point")
                .document(user?.did!!)
                .set(it)
                .addOnSuccessListener {
                    Logger.d("[addOnSuccessListener]")
                    onPointListResponseListener?.onCompleteListener(history.apply {
                        isSuccess = true
                    })
                    changePointSave(user, onResponseListener)
                }.addOnFailureListener{
                    Logger.d("[addOnFailureListener] ${it.message}")
                    val responseDTO = UserInfoResponseDTO(null).apply {
                        isSuccess = false
                        msg = it.message!!+"\n취소 중 에러발생: 꼭! 포인트 적립/사용으로 취소해주시기 바랍니다."
                    }
                    onResponseListener?.onCompleteListener(responseDTO)
                }
        }
    }

    private fun parseMap(list: ArrayList<*>?, position: Int) : HashMap<String,Any?>{
        return list?.toArray()?.get(position) as HashMap<String,Any?>
    }

    private fun parseList(list: ArrayList<*>?, position: Int) : PointInfoResponseDTO{
        return list?.get(position) as PointInfoResponseDTO
    }
}