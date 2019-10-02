package com.homeground.app.view.point.search.bean

data class UserInfoResponseDTO(val name: String? = null,
                               val phone: String? = null,
                               val phone_id: String? = null,
                               val birthday: String? = null,
                               val note: String? = null,
                               val sign_up_date: String? = null,
                               val point: Long? = null,
                               val last_point_date: String? = null)
