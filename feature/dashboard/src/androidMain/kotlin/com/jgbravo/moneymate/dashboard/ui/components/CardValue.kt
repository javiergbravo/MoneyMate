package com.jgbravo.moneymate.dashboard.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgbravo.moneymate.core.ui.theme.ExtraColor
import com.jgbravo.moneymate.core.ui.theme.MoneyMateTheme
import com.jgbravo.moneymate.dashboard.models.Currency
import com.jgbravo.moneymate.dashboard.models.MovementType
import com.jgbravo.moneymate.dashboard.models.Pocket

@Composable
fun CardValue(
    pocket: Pocket,
    type: MovementType,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (type == MovementType.INCOME) "Ingresos" else "Gastos",
                style = MaterialTheme.typography.headlineSmall,
                color = if (type == MovementType.INCOME) {
                    Color(ExtraColor.Green)
                } else {
                    Color(ExtraColor.RedStop)
                }
            )
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
            Text(
                text = "${pocket.amount} ${pocket.currency.symbol}",
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}

@Preview
@Composable
fun CardValuePreview() {
    MoneyMateTheme {
        CardValue(
            pocket = Pocket(
                amount = 1000.0,
                currency = Currency.EUR
            ),
            type = MovementType.INCOME
        )
    }
}