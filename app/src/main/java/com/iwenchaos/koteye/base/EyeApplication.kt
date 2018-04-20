package com.iwenchaos.koteye.base

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

/**
 * Created by chaos
 * on 2018/4/20. 10:30
 * 文件描述：
 */
class EyeApplication : Application() {

    private var refWatcher: RefWatcher? = null

    override fun onCreate() {
        super.onCreate()
        refWatcher = leackCanary()
    }

    private fun leackCanary(): RefWatcher? =
            if (LeakCanary.isInAnalyzerProcess(this)) {
                RefWatcher.DISABLED
            } else
                LeakCanary.install(this)





}