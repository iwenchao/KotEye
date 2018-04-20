package com.iwenchaos.koteye.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.multidex.MultiDex
import com.iwenchaos.koteye.EYE_APPLICATION
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.tencent.tinker.anno.DefaultLifeCycle
import com.tencent.tinker.loader.app.DefaultApplicationLike
import com.tencent.tinker.loader.shareutil.ShareConstants

/**
 * Created by chaos
 * on 2018/4/20. 10:30
 * 文件描述：
 */
//这个构造器显得沉重，需要以次构造器继承的方式
@DefaultLifeCycle(application = "com.iwenchaos.koteye.base.EyeApplication", flags = ShareConstants.TINKER_ENABLE_ALL)
class EyeApplication(application: Application,
                     tinkerFlags: Int,
                     tinkerLoadVerifyFlag: Boolean,
                     applicationStartElapsedTime: Long,
                     applicationStartMillisTime: Long,
                     tinkerResultIntent: Intent
) : DefaultApplicationLike(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
        applicationStartMillisTime, tinkerResultIntent) {

    private var refWatcher: RefWatcher? = null

    override fun onBaseContextAttached(base: Context?) {
        super.onBaseContextAttached(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        refWatcher = leackCanary()
        logger()
        application.registerActivityLifecycleCallbacks(activityCallback)
    }

    private fun leackCanary(): RefWatcher? =
            if (LeakCanary.isInAnalyzerProcess(application)) {
                RefWatcher.DISABLED
            } else
                LeakCanary.install(application)


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