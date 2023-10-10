package com.jgbravo.moneymate.core.data

sealed interface Result {
    data object Success : Result
    data class Failure(val exception: Exception) : Result
}