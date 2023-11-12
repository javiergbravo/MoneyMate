package com.jgbravo.moneymate.dashboard.models

data class Budget(
    val mainCurrency: Currency,
    val incomeAmount: Double,
    val fixedCostAmount: Double,
    val variableCostAmount: Double,
    val investmentsAmount: Double,
    val savingsAmount: Double,
)

data class BudgetDetails(
    val mainCurrency: Currency,
    val incomes: List<Pocket>,
    val fixedCost: List<Movement>,
    val variableCost: List<Pocket>
)