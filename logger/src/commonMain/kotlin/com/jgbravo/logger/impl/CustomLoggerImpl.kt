package com.jgbravo.logger.impl

import com.jgbravo.logger.CustomLogger
import com.jgbravo.logger.CustomLogger.DatabaseAction
import io.github.aakira.napier.Napier
import kotlin.time.TimeSource.Monotonic

abstract class CustomLoggerImpl : CustomLogger {

    private var lastLogTime = 0L

    protected open val customTag: String? = null

    override fun d(message: String) {
        return Napier.d(tag = customTag, message = message)
    }

    override fun d(tag: String, message: String) {
        return Napier.d(tag = tag, message = message)
    }

    override fun i(message: String) {
        return Napier.i(tag = customTag, message = message)
    }

    override fun i(tag: String, message: String) {
        return Napier.i(tag = tag, message = message)
    }

    override fun w(message: String) {
        return Napier.w(tag = customTag, message = message)
    }

    override fun w(throwable: Throwable) {
        return Napier.w(tag = customTag, throwable = throwable, message = throwable.stackTraceToString())
    }

    override fun e(message: String) {
        return Napier.e(tag = customTag, message = message)
    }

    override fun e(throwable: Throwable) {
        return Napier.e(tag = customTag, throwable = throwable, message = throwable.stackTraceToString())
    }

    override fun time(message: String) {
        val time = if (lastLogTime == 0L) {
            0L
        } else {
            Monotonic.markNow().elapsedNow().inWholeMilliseconds - lastLogTime
        }
        Napier.wtf(tag = customTag, message = "[LogTime: ${time / MILLIS_TO_SECONDS}s] $message")
        lastLogTime = Monotonic.markNow().elapsedNow().inWholeMilliseconds
    }

    override fun db(message: String, dbAction: DatabaseAction) {
        Napier.d(tag = "[$DATABASE - ${dbAction.action}]", message = message)
    }

    override fun http(message: String) {
        Napier.d(tag = API_CALL, message = message)
    }

    private companion object {
        private const val API_CALL = "[Api Call - OkHttp]"
        private const val DATABASE = "Database"
        private const val MILLIS_TO_SECONDS = 1000.0
    }
}