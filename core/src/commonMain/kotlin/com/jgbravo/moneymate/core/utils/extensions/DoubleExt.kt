package com.jgbravo.moneymate.core.utils.extensions

import com.jgbravo.moneymate.core.utils.constants.PERCENT
import kotlin.math.round

fun Float.percent() = round(this * PERCENT)