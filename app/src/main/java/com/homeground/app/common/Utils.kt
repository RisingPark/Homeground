package com.homeground.app.common

import android.content.Context
import android.content.pm.PackageManager
import java.lang.Long.parseLong
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class Utils {
    companion object {

        fun isEmpty(s: Any?): Boolean {
            if (s == null) {
                return true
            }
            if (s is String && s.trim { it <= ' ' }.length == 0) {
                return true
            }
            if (s is Map<*, *>) {
                return s.isEmpty()
            }
            if (s is List<*>) {
                return s.isEmpty()
            }
            return if (s is Array<*>) {
                s.size == 0
            } else false
        }

        fun addComma(num: Long?): String {
            val df = DecimalFormat("#,##0")
            return try {
                df.format(num)
            } catch (e: Exception) {
                "-"
            }

        }

        fun addCommaStrNum(num: String?): String {
            val df = DecimalFormat("#,##0")
            return try {
                df.format(parseLong(num))
            } catch (e: Exception) {
                "-"
            }
        }

        fun addCommaP(num: Long?): String {
            return try {
                addComma(num) + " P"
            } catch (e: NumberFormatException) {
                "-"
            }

        }

        fun addCommaP(num: String?): String {
            return try {
                addCommaStrNum(num) + " P"
            } catch (e: NumberFormatException) {
                "-"
            }

        }

        fun getCurrentDate(): String {
            try {
                val time: TimeZone
                val date = Date()
                val df = SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.KOREA
                )
                time = TimeZone.getTimeZone("Asia/Seoul")
                df.timeZone = time
                return df.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
                return ""
            }
        }

        /**
         * 휴대폰 번호 유효성 체크
         *
         * @param cellphoneNumber
         * @return boolean
         */
        fun isValidPhoneNumber(cellphoneNumber: String): Boolean {
            var returnValue = false
            val regex =
                "^\\s*(010|011|012|013|014|015|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$"
            val p = Pattern.compile(regex)
            val m = p.matcher(cellphoneNumber)

            if (m.matches()) {
                returnValue = true
            }
            return returnValue
        }



        /**
         * Get Application Version Name
         *
         * @param context Context
         * @return Version name of Application
         * @throws PackageManager.NameNotFoundException
         */
        fun getApplicationVersionName(context: Context, defaultVersionName: String): String {
            try {
                val packageManager = context.packageManager
                val info = packageManager.getPackageInfo(context.packageName, 0)
                return info.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return defaultVersionName
            }
        }

    }
}