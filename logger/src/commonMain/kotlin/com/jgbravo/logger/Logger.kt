package com.jgbravo.logger

import com.jgbravo.logger.CustomLogger.DatabaseAction
import com.jgbravo.logger.impl.AppCustomLoggerImpl

object Logger {

    private val customLogger: CustomLogger by lazy { AppCustomLoggerImpl() }

    fun init() = LoggerInitializer.init()

    fun d(message: String) = customLogger.d(message = message)

    fun d(tag: String, message: String) = customLogger.d(tag = tag, message = message)

    fun i(message: String) = customLogger.i(message = message)

    fun i(tag: String, message: String) = customLogger.i(tag = tag, message = message)

    fun w(message: String) = customLogger.w(message = message)

    fun w(throwable: Throwable) = customLogger.w(throwable = throwable)

    fun e(message: String) = customLogger.e(message = message)

    fun e(throwable: Throwable) = customLogger.e(throwable = throwable)

    fun time(message: String) = customLogger.time(message = message)

    fun db(message: String, dbAction: DatabaseAction) = customLogger.db(message = message, dbAction = dbAction)

    fun http(message: String) = customLogger.http(message = message)
}