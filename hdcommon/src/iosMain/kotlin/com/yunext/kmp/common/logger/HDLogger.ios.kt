package com.yunext.kmp.common.logger

import kotlin.native.concurrent.ThreadLocal

actual interface HDLogger {
    actual var debug: Boolean
    actual fun d(tag: String, msg: String)
    actual fun i(tag: String, msg: String)
    actual fun w(tag: String, msg: String)
    actual fun e(tag: String, msg: String)

    @ThreadLocal
    actual companion object : HDLogger {
        override var debug: Boolean = false
        actual override fun d(tag: String, msg: String) {
        }

        actual override fun i(tag: String, msg: String) {
        }

        actual override fun w(tag: String, msg: String) {
        }

        actual override fun e(tag: String, msg: String) {
        }

    }

}