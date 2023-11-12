package com.jgbravo.moneymate.core.utils.extensions

import com.jgbravo.moneymate.core.utils.constants.PERCENT
import kotlin.math.round

fun Double.percentOf(totalAmount: Double): Double = round(this / totalAmount * PERCENT)