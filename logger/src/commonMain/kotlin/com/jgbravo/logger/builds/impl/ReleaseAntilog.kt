package com.jgbravo.logger.builds.impl

import com.jgbravo.logger.builds.ReleaseLoggerBuild
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.LogLevel.ERROR
import io.github.aakira.napier.LogLevel.WARNING

class ReleaseAntilog : Antilog(), ReleaseLoggerBuild {

    override fun performLog(priority: LogLevel, tag: String?, throwable: Throwable?, message: String?) {
        when (priority) {
            WARNING -> sendWarning(
                tag = tag,
                throwable = throwable,
                message = message ?: throwable?.stackTraceToString() ?: ""
            )
            ERROR -> sendError(
                tag = tag,
                throwable = throwable,
                message = message ?: throwable?.stackTraceToString() ?: ""
            )
            else -> Unit
        }
    }

    override fun sendError(tag: String?, message: String, throwable: Throwable?) = Unit

    override fun sendWarning(tag: String?, message: String, throwable: Throwable?) = Unit
}