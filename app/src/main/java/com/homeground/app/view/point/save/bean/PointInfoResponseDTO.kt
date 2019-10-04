package com.homeground.app.view.point.save.bean

import com.homeground.app.common.bean.BaseResponseDTO

data class PointInfoResponseDTO(var did: String?,
                                var name: String?,
                                var phone: String?,
                                var state: String?,
                                var date: String?,
                                var point: Long?) : BaseResponseDTO()