package com.homeground.app.common.base

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel(){
    var TAG = this.javaClass.simpleName
}