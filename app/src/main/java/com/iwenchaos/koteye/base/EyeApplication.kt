package com.iwenchaos.koteye.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.multidex.MultiDex
import com.iwenchaos.koteye.EYE_APPLICATION
import com.iwenchaos.koteye.utils.DisplayManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.tinker.lib.tinker.TinkerInstaller
import com.tencent.tinker.loader.app.DefaultApplicationLike
import kotlin.properties.Delegates


/**
 * Created by chaos
 * on 2018/4/20. 10:30
 * 文件描述：
 */

class EyeApplication constructor(application: Application,
                                 tinkerFlags: Int,
                                 tinkerLoadVerifyFlag: Boolean,
                                 applicationStartElapsedTime: Long,
                                 applicationStartMillisTime: Long,
                                 tinkerResultIntent: Intent
) : DefaultApplicationLike(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
        applicationStartMillisTime, tinkerResultIntent) {
    //leak canary
    var refWatcher: RefWatcher? = null

    companion object {

        var context: Context by Delegates.notNull()
            private set


    }


    override fun onBaseContextAttached(base: Context?) {
        super.onBaseContextAttached(base)
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base)
        TinkerInstaller.install(this)
//        TinkerInstaller.install(this, DefaultLoadReporter(application), DefaultPatchReporter(application),
//                DefaultPatchListener(application), QuickResultService::class.java, UpgradePatch())
//        val tinker = Tinker.with(application)

    }

    override fun onCreate() {
        super.onCreate()
        context = application.applicationContext
        refWatcher = leakCanary()
        DisplayManager.init(context)
        logger()
        CrashReport.initCrashReport(context,"bb315ff392",false)//注册时申请的APPID
        application.registerActivityLifecycleCallbacks(activityCallback)
    }


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

    private fun leakCanary(): RefWatcher? =
            if (LeakCanary.isInAnalyzerProcess(application)) {
                RefWatcher.DISABLED
            } else
                LeakCanary.install(application)


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