package com.jgbravo.moneymate.dashboard.models

enum class Currency(val value: String, val symbol: String) {
    EUR("EUR", "€"), // Euro
    USD("USD", "$"), // Dólar americano
    GBP("GBP", "£"), // Libra
    JPY("JPY", "¥"), // Yen
    CNY("CNY", "¥"), // Yuan
    CHF("CHF", "Fr"), // Franco suizo
    CAD("CAD", "CA$"), // Dólar canadiense
    AUD("AUD", "AUD$"), // Dólar australiano
}