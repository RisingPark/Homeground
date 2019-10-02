package com.homeground.app.common.bean

open class BaseResponseDTO {

    var isSuccess:Boolean = false
    var msg: String = ""

    constructor()

    constructor(isSuccess: Boolean , msg: String) {
        this.isSuccess = isSuccess
        this.msg = msg
    }
}