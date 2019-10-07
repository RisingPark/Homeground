package com.homeground.app.view.point.save.bean

import com.homeground.app.common.bean.BaseResponseDTO
import java.io.Serializable

data class PointInfoListResponseDTO(var pointInfoResponseDTO: ArrayList<PointInfoResponseDTO>?):
    Serializable, BaseResponseDTO()