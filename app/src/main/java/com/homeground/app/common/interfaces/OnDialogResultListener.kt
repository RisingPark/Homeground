package com.homeground.app.common.interfaces

interface OnDialogResultListener{
    /** 성공시 - Yes클릭시 등  */
    fun resultSuccess(data: String)

    /** 실패시 - No클릭시 등  */
    fun resultFailure(failMsg: String)

    /** 실패시 - 사용자 선택 취소시 또는 Back key 누를경우  */
    fun resultCancel()
}