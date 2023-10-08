package com.jgbravo.moneymate.core

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform