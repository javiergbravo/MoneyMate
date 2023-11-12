package com.jgbravo.moneymate.dashboard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgbravo.moneymate.core.ui.theme.ExtraColor
import com.jgbravo.moneymate.core.ui.theme.MoneyMateTheme
import com.jgbravo.moneymate.dashboard.models.Currency
import com.jgbravo.moneymate.dashboard.models.Movement
import com.jgbravo.moneymate.dashboard.models.MovementType
import com.jgbravo.moneymate.dashboard.models.MovementType.EXPENSE
import com.jgbravo.moneymate.dashboard.models.MovementType.INCOME
import com.jgbravo.moneymate.dashboard.models.MovementType.INVESTMENT
import com.jgbravo.moneymate.dashboard.models.MovementType.SAVING

@Composable
fun CardValue(
    movement: Movement,
    totalAmount: Double = 0.0,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (icon != null) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = icon,
                    contentDescription = "Movement icon"
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = movement.concept ?: when (movement.type) {
                            INCOME -> "Ingresos"
                            EXPENSE -> "Gastos"
                            INVESTMENT -> "Inversión"
                            SAVING -> "Ahorro"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.scrim
                    )
                    val amount = when (movement.type) {
                        EXPENSE -> "-${movement.amount}"
                        else -> "${movement.amount}"
                    }
                    Text(
                        text = "$amount ${movement.currency.symbol}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.scrim
                    )
                }
                if (totalAmount != 0.0) {
                    Spacer(modifier = Modifier.padding(3.dp))
                    ProgressPercent(
                        modifier = Modifier.fillMaxWidth(),
                        progress = (movement.amount / totalAmount).toFloat(),
                        progressColor = when (movement.type) {
                            EXPENSE -> Color(ExtraColor.RedStop)
                            else -> Color(ExtraColor.Green)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CardValuePreview() {
    MoneyMateTheme {
        CardValue(
            movement = Movement(
                amount = 1000.0,
                currency = Currency.EUR,
                type = MovementType.SAVING
            ),
            icon = Icons.Default.SaveAlt,
            totalAmount = 2700.0
        )
    }
}