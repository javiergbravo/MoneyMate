package com.jgbravo.logger.builds

interface ReleaseLoggerBuild {

    /** Send ERROR reports to Crashlytics, Sentry or another error monitoring platform **/
    fun sendError(tag: String?, message: String, throwable: Throwable?)

    /** Send WARNING reports to Crashlytics, Sentry or another error monitoring platform **/
    fun sendWarning(tag: String?, message: String, throwable: Throwable?)
}