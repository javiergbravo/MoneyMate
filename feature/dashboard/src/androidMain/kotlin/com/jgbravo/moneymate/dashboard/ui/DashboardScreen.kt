package com.jgbravo.moneymate.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgbravo.moneymate.core.ui.theme.MoneyMateTheme
import com.jgbravo.moneymate.dashboard.models.Budget
import com.jgbravo.moneymate.dashboard.models.dummy.Dummy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    budget: Budget,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                title = { Text(text = "Portfolio") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Total Balance",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "${budget.incomeAmount} ${budget.mainCurrency.symbol}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            /*Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                CardValue(
                    pocket = Pocket(
                        amount = budget.incomeAmount,
                        currency = budget.mainCurrency

                    ),
                    type = MovementType.INCOME,
                )
                CardValue(
                    pocket = Pocket(
                        amount = 1218.33,
                        currency = budget.mainCurrency

                    ),
                    type = MovementType.EXPENSE,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                CardValue(
                    pocket = Pocket(
                        amount = 1000.00,
                        currency = budget.mainCurrency

                    ),
                    type = MovementType.INVESTMENT,
                )
                CardValue(
                    pocket = Pocket(
                        amount = 400.00,
                        currency = budget.mainCurrency

                    ),
                    type = MovementType.SAVING,
                )
            }*/
        }
    }
}

data class IncomeConcept(val name: String, val amount: Double)

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    MoneyMateTheme {
        DashboardScreen(
            budget = Dummy.FakeBudget
        )
    }
}