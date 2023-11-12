package com.jgbravo.moneymate.core.utils.extensions

import com.jgbravo.moneymate.core.utils.constants.PERCENT
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt

fun Float.percent() = round(this * PERCENT)

fun Float.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}