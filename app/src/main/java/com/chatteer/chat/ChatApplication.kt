package com.chatteer.chat

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import hmju.http.tracking.HttpTracking
import timber.log.Timber

/**
 * Description : ChatApplication
 *
 * Created by juhongmin on 2024. 9. 15.
 */
@HiltAndroidApp
class ChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initTracking()
    }

    private fun initTimber() {
        if (getString(R.string.BuildType) != "release") {
            Timber.plant(object : Timber.DebugTree() {

                override fun createStackElementTag(element: StackTraceElement): String {
                    val str = StringBuilder("ChatTimber")
                    try {
                        val className = element.className.substringAfterLast(".")
                            .substringBefore("$")
                        val methodName = element.methodName.substringAfterLast(".")
                        str.append(className)
                            .append(":")
                            .append(methodName)
                    } catch (ex: Exception) {
                        // ignore
                    }
                    return str.toString()
                }

                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, tag, message, t)
                    if (t != null) {
                        // firebaseCrashlytics.recordException(t)
                    }
                }
            })
        }
    }

    private fun initTracking() {
        if (getString(R.string.BuildType) != "release") {
            HttpTracking.Builder()
                .setBuildType(true)
                .setWifiShare(true)
                .setLogMaxSize(1000)
                .build(this)
        }
    }
}
