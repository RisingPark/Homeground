package com.homeground.app.common

import android.app.Activity
import android.content.Intent
import com.homeground.app.view.main.MainActivity

class CommonOpener {
    companion object{
        fun openAnyActivity(activity: Activity, cls: Class<*>?){
            activity.startActivity(Intent(activity, cls))
        }

        fun openMainActivity(activity: Activity){
            activity.startActivity(Intent(activity, MainActivity::class.java))
            activity.finish()
        }
    }
}