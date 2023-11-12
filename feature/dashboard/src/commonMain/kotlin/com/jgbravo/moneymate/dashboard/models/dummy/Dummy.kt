package com.jgbravo.moneymate.dashboard.models.dummy

import com.jgbravo.moneymate.core.ui.theme.ExtraColor
import com.jgbravo.moneymate.dashboard.models.Budget
import com.jgbravo.moneymate.dashboard.models.BudgetDetails
import com.jgbravo.moneymate.dashboard.models.Currency
import com.jgbravo.moneymate.dashboard.models.Movement
import com.jgbravo.moneymate.dashboard.models.MovementType
import com.jgbravo.moneymate.dashboard.models.Pocket
import com.jgbravo.moneymate.dashboard.models.Wallet
import kotlin.math.round

object Dummy {

    val BancoSabadell = Wallet(
        name = "Banco Sabadell",
        color = ExtraColor.Purple,
        pockets = listOf(
            Pocket(
                amount = 99.67,
                currency = Currency.EUR
            )
        )
    )

    val Bbva = Wallet(
        name = "BBVA",
        color = ExtraColor.Blue,
        pockets = listOf(
            Pocket(
                amount = 383.33,
                currency = Currency.EUR
            )
        )
    )

    val Revolut = Wallet(
        name = "Revolut",
        color = ExtraColor.Pink,
        pockets = listOf(
            Pocket(
                amount = 550.0,
                currency = Currency.EUR
            ),
            Pocket(
                amount = 0.0,
                currency = Currency.GBP
            ),
            Pocket(
                amount = 0.0,
                currency = Currency.USD
            )
        )
    )

    val TradeRepublic = Wallet(
        name = "Trade Republic",
        color = ExtraColor.Platinum,
        pockets = listOf(
            Pocket(
                amount = 485.0,
                currency = Currency.EUR
            )
        )
    )

    val MyInvestor = Wallet(
        name = "MyInvestor",
        color = ExtraColor.Magenta,
        pockets = listOf(
            Pocket(
                amount = 0.0,
                currency = Currency.EUR
            )
        )
    )

    val InteractiveBrokers = Wallet(
        name = "Interactive Brokers",
        color = ExtraColor.RedBrown,
        pockets = listOf(
            Pocket(
                amount = 1200.0,
                currency = Currency.EUR
            )
        )
    )

    val ImaginBank = Wallet(
        name = "ImaginBank",
        color = ExtraColor.Green,
        pockets = listOf(
            Pocket(
                amount = 0.0,
                currency = Currency.EUR
            )
        )
    )

    val FakeBudgetDetails = BudgetDetails(
        mainCurrency = Currency.EUR,
        incomes = listOf(
            Pocket(
                amount = 2718.0,
                currency = Currency.EUR
            )
        ),
        fixedCost = listOf(
            Movement(
                amount = 500.0,
                currency = Currency.EUR,
                concept = "Alquiler",
                date = "01/01/2021",
                type = MovementType.EXPENSE
            )
        ),
        variableCost = listOf()
    )

    val FakeBudget = Budget(
        mainCurrency = FakeBudgetDetails.mainCurrency,
        incomeAmount = FakeBudgetDetails.incomes.sumOf { it.amount },
        fixedCostAmount = FakeBudgetDetails.fixedCost.sumOf { it.amount },
        variableCostAmount = FakeBudgetDetails.variableCost.sumOf { it.amount },
        investmentsAmount = 1200.00,
        savingsAmount = 300.0
    )

    fun getMonthlyMovements(totalAmount: Double): List<Movement> {
        val fixedCost = 568.33
        val variableCost = 650.0
        val investment = 1000.0
        val saving = 400.0
        return listOf(
            Movement(
                concept = "Gastos Fijos",
                amount = fixedCost,
                currency = Currency.EUR,
                type = MovementType.EXPENSE
            ),
            Movement(
                concept = "Gastos Variables",
                amount = variableCost,
                currency = Currency.EUR,
                type = MovementType.EXPENSE
            ),
            Movement(
                concept = "Inversión",
                amount = investment,
                currency = Currency.EUR,
                type = MovementType.INCOME
            ),
            Movement(
                concept = "Ahorro",
                amount = saving,
                currency = Currency.EUR,
                type = MovementType.INCOME
            ),
            Movement(
                concept = "Gastos Extra",
                amount = round(totalAmount - listOf(fixedCost, variableCost, investment, saving).sum()),
                currency = Currency.EUR,
                type = MovementType.INCOME
            )
        )
    }
}