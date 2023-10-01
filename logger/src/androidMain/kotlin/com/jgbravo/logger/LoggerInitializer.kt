package com.jgbravo.logger

import com.jgbravo.logger.builds.impl.ReleaseAntilog
import io.github.aakira.napier.BuildConfig
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual object LoggerInitializer {

    actual fun init() {
        if (BuildConfig.DEBUG) {
            Napier.base(DebugAntilog())
        } else {
            Napier.base(ReleaseAntilog())
        }
    }
}