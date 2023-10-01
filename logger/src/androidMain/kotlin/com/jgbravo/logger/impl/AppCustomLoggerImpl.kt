package com.jgbravo.logger.impl

import java.util.regex.Pattern

actual class AppCustomLoggerImpl : CustomLoggerImpl() {

    override val customTag: String
        get() {
            val stackTraceElements = Throwable().stackTrace
            for (stackTraceElement in stackTraceElements) {
                if (isAppClass(stackTraceElement)
                    && !isAppLoggerClass(stackTraceElement)
                    && !isLoggerClass(stackTraceElement)
                ) {
                    return buildTag(stackTraceElement)
                }
            }
            return "Unknown"
        }

    private fun isLoggerClass(stackTraceElement: StackTraceElement): Boolean {
        return stackTraceElement.className.contains(CustomLoggerImpl::class.java.name)
    }

    private fun isAppClass(stackTraceElement: StackTraceElement): Boolean {
        return stackTraceElement.className.startsWith(PACKAGE_APP)
    }

    private fun isAppLoggerClass(stackTraceElement: StackTraceElement): Boolean {
        return stackTraceElement.className.contains(this::class.java.name)
    }

    private fun buildTag(stackTraceElement: StackTraceElement) = String.format(
        format = "[C:%s] [F:%s] [L:%s] ",
        createStackElementTag(stackTraceElement),
        stackTraceElement.methodName,
        stackTraceElement.lineNumber
    )

    private fun createStackElementTag(element: StackTraceElement): String {
        var tag = element.className
        val m = PATTERN_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        tag = tag.substring(tag.lastIndexOf('.') + 1)
        return if (tag.length <= MAX_TAG_LENGTH) {
            tag
        } else {
            tag.substring(0, MAX_TAG_LENGTH)
        }
    }

    private companion object {
        private const val PACKAGE_APP = "com.jgbravo"
        private val PATTERN_CLASS: Pattern = Pattern.compile("(\\$\\d+)+$")
        private const val MAX_TAG_LENGTH = 23
    }
}