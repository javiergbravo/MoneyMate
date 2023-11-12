package com.jgbravo.moneymate.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgbravo.moneymate.core.ui.theme.AppTheme
import com.jgbravo.moneymate.core.ui.theme.MoneyMateTheme
import com.jgbravo.moneymate.dashboard.models.Budget
import com.jgbravo.moneymate.dashboard.models.Movement
import com.jgbravo.moneymate.dashboard.models.MovementType.EXPENSE
import com.jgbravo.moneymate.dashboard.models.dummy.Dummy
import com.jgbravo.moneymate.dashboard.ui.components.CardValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    budget: Budget,
    monthlyMovements: List<Movement>,
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
            LazyColumn(
                modifier.padding(top = AppTheme.dimens.normal),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.normal)
            ) {
                items(count = monthlyMovements.size) {
                    val movement = monthlyMovements[it]
                    CardValue(
                        movement = movement,
                        totalAmount = budget.incomeAmount,
                        icon = when (movement.type) {
                            EXPENSE -> Icons.AutoMirrored.Filled.ExitToApp
                            else -> Icons.Default.SaveAlt
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    MoneyMateTheme {
        DashboardScreen(
            budget = Dummy.FakeBudget,
            monthlyMovements = Dummy.getMonthlyMovements(2700.0)
        )
    }
}