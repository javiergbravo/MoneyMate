package com.jgbravo.logger

actual object LoggerInitializer {

    /** This method is empty because iOS needs to be initialized in AppDelegate
     *
     * #if DEBUG
     *
     * NapierProxyKt.debugBuild()
     *
     * #else
     *
     * FirebaseApp.configure()
     *
     * NapierProxyKt.releaseBuild(antilog: CrashlyticsAntilog(
     *     crashlyticsAddLog: { priority, tag, message in
     *         Crashlytics.crashlytics().log("\(String(describing: tag)): \(String(describing: message))")
     * },
     *     crashlyticsSendLog: { throwable in
     *         Crashlytics.crashlytics().record(error: throwable)
     * }))
     * #endif
     *
     * more info: https://github.com/AAkira/Napier
     */
    actual fun init() = Unit
}