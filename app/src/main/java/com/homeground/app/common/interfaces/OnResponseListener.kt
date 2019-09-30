package com.homeground.app.common.interfaces

interface OnResponseListener<T> {
    fun onSuccessListener(response: T)
    fun onFailureListener(response: T)
}
