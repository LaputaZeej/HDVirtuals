package com.yunext.kmp.context

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import java.lang.ref.WeakReference
import kotlin.jvm.Throws

//public actual typealias HDContext = Application

public actual class HDContext actual constructor() {
    private lateinit var _context: Any
    actual val context: Any
        get() = _context

    actual fun init(ctx: Any) {
        if (::_context.isInitialized) {
            println("[hd]warning : HDContext::_context is already init!")
            return
        }
        require(ctx is Application) {
            println("[hd]warning : ctx must be context!")
        }

        try {
            ctx.unregisterActivityLifecycleCallbacks(callback)
            topActivityInternal.clear()
        } catch (e: Exception) {
            println("[hd]warn : unregisterActivityLifecycleCallbacks fail $e")
        }
        ctx.registerActivityLifecycleCallbacks(callback)
        _context = ctx
    }

    private var topActivityInternal: WeakReference<Activity?> = WeakReference(null)
    val topActivity: Activity?
        get() = topActivityInternal.get()

    private val callback = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

        }

        override fun onActivityStarted(activity: Activity) {

        }

        override fun onActivityResumed(activity: Activity) {
            topActivityInternal = WeakReference(activity)
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {

        }

    }

}


val HDContext.application: Application
    // 未初始化
    @Throws(HDContextException::class)
    get() = try {
        this.context as Application
    } catch (e: Throwable) {
        throw HDContextException("context cast application fail.", cause = e)
    }