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

        fun setDeviceName(context: Context, value: String) {
            try {
                val setting = context.getSharedPreferences(PREFS_USER_INFO, Context.MODE_PRIVATE)
                val editor = setting.edit()
                editor.putString("device_name", value)
                editor.apply()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun getDeviceName(context: Context): String {
            try {
                val setting = context.getSharedPreferences(PREFS_USER_INFO, Context.MODE_PRIVATE)
                return setting.getString("device_name", "")
            } catch (e: Exception) {
                e.printStackTrace()
                return ""
            }
        }

        fun setFirstLauncher(context: Context, value: Boolean) {
            try {
                val setting = context.getSharedPreferences(PREFS_USER_INFO, Context.MODE_PRIVATE)
                val editor = setting.edit()
                editor.putBoolean("first_launcher", value)
                editor.apply()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun isFirstLauncher(context: Context): Boolean {
            try {
                val setting = context.getSharedPreferences(PREFS_USER_INFO, Context.MODE_PRIVATE)
                return setting.getBoolean("first_launcher", true)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        }

    }
}