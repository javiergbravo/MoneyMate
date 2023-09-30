package com.jgbravo.moneymate

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform