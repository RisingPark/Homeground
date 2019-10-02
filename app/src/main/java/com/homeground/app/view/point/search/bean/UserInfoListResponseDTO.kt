package com.homeground.app.view.point.search.bean

import com.homeground.app.common.bean.BaseResponseDTO

data class UserInfoListResponseDTO(val userInfoResponseDTO: List<UserInfoResponseDTO>?)
    : BaseResponseDTO()