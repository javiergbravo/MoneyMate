package com.jgbravo.moneymate.dashboard.models

data class Wallet(
    val name: String,
    val color: Long,
    val pockets: List<Pocket>
)

data class Pocket(
    val amount: Double,
    val currency: Currency
)

data class Movement(
    val amount: Double,
    val currency: Currency,
    val concept: String? = null,
    val date: String? = null,
    val type: MovementType
)

enum class MovementType {
    INCOME, EXPENSE, INVESTMENT, SAVING
}