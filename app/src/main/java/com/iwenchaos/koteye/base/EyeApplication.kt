package com.iwenchaos.koteye.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.iwenchaos.koteye.EYE_APPLICATION
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


    private var refWatcher: RefWatcher? = null

    override fun onCreate() {
        super.onCreate()
        refWatcher = leackCanary()
        logger()
        registerActivityLifecycleCallbacks(activityCallback)
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
                .tag(EYE_APPLICATION)
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    private val activityCallback = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {

        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            Logger.d("onCreate() ", activity?.componentName?.className)
        }
    }

}