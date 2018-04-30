package com.iwenchaos.koteye.utils

import android.content.Context
import android.content.pm.PackageManager

/**
 * Created by chaos
 * on 2018/4/30. 13:36
 * 文件描述：
 */
class AppUtil private constructor() {
    init {
        throw Error("Do not need instantiate")
    }

    companion object {
        private val DEBUG = true
        private val TAG = "AppUtil"

        fun getVerCode(context: Context): Int {
            var vCode = 0
            try {
                val packageName = context.packageName
                vCode = context.packageManager.getPackageInfo(packageName, 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return vCode
        }
        fun getVerName(context: Context): String {
            var vName = ""
            try {
                val packageName = context.packageName
                vName = context.packageManager.getPackageInfo(packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return vName
        }
    }
}