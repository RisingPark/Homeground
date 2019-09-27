package com.homeground.app

import android.app.Activity
import android.app.Application
import com.homeground.app.di.module
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.startKoin
import java.util.*


class HApplication : Application() {

    val activityList = LinkedList<Activity>()

    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, module)
        setLogger()
    }

    private fun setLogger() {

        // Release 빌드 시 로그 제거
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}