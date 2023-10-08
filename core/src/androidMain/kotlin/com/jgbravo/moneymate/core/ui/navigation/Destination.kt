package com.jgbravo.moneymate.core.ui.navigation

interface Destination {
    val route: String
    val params: ArrayList<String>

    operator fun invoke(): String
}