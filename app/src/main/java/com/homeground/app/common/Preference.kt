package com.homeground.app.common

import android.content.Context

class Preference {
    companion object{
        const val PREFS_USER_INFO = "prefs_user_info"

        fun setPercent(context: Context, value: Int) {
            try {
                val setting = context.getSharedPreferences(PREFS_USER_INFO, Context.MODE_PRIVATE)
                val editor = setting.edit()
                editor.putInt("percent", value)
                editor.apply()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun getPercent(context: Context): Int {
            try {
                val setting = context.getSharedPreferences(PREFS_USER_INFO, Context.MODE_PRIVATE)
                return setting.getInt("percent", 5)
            } catch (e: Exception) {
                e.printStackTrace()
                return 5
            }

        }
    }
}