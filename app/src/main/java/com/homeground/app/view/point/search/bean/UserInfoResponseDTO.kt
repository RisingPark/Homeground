package com.homeground.app.view.point.search.bean

import com.homeground.app.common.bean.BaseResponseDTO
import java.io.Serializable

data class UserInfoResponseDTO(var name: String? = null,
                               var phone: String? = null,
                               var phone_id: String? = null,
                               var birthday: String? = null,
                               var note: String? = null,
                               var sign_up_date: String? = null,
                               var point: Long? = null,
                               var last_point_date: String? = null,
                               var did: String? = null,
                               var checkSum: String? = null) : Serializable, BaseResponseDTO()
