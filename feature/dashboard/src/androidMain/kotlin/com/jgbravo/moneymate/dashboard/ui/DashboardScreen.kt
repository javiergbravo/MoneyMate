package com.jgbravo.moneymate.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgbravo.moneymate.core.ui.theme.MoneyMateTheme
import com.jgbravo.moneymate.dashboard.models.Budget
import com.jgbravo.moneymate.dashboard.models.dummy.Dummy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    budget: Budget,
    modifier: Modifier = Modifier
) {
    val incomeConcepts = listOf(
        IncomeConcept("Salario", 3500.0),
        IncomeConcept("Ventas", 1000.0),
        IncomeConcept("Inversiones", 500.0),
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            title = { Text(text = "Budget") },
//            backgroundColor = MaterialTheme.colors.primarySurface
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                IncomeSummary(budget.incomeAmount)
            }

            items(incomeConcepts) { concept ->
                IncomeConceptItem(concept)
            }
        }
    }
}

@Composable
fun IncomeSummary(totalIncome: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ingresos Totales",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "$$totalIncome",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun IncomeConceptItem(concept: IncomeConcept) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = concept.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "$${concept.amount}", style = MaterialTheme.typography.bodyLarge)
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