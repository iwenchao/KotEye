package com.iwenchaos.koteye.base

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

/**
 * Created by chaos
 * on 2018/4/20. 10:30
 * 文件描述：
 */
class EyeApplication : Application() {
    private val EYEAPPLICATION: String = "EyeApplication"

    private var refWatcher: RefWatcher? = null

    override fun onCreate() {
        super.onCreate()
        refWatcher = leackCanary()
        logger()
    }

    private fun leackCanary(): RefWatcher? =
            if (LeakCanary.isInAnalyzerProcess(this)) {
                RefWatcher.DISABLED
            } else
                LeakCanary.install(this)


    private fun logger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .methodCount(3)
                .methodOffset(5)
                .tag(EYEAPPLICATION)
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }


}