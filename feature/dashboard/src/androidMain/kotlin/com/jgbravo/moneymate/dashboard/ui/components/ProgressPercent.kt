package com.jgbravo.moneymate.dashboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgbravo.moneymate.core.ui.theme.AppTheme
import com.jgbravo.moneymate.core.ui.theme.ExtraColor
import com.jgbravo.moneymate.core.ui.theme.MoneyMateTheme
import com.jgbravo.moneymate.core.utils.extensions.percent
import com.jgbravo.moneymate.core.utils.extensions.roundTo

@Composable
fun ProgressPercent(
    progress: Float,
    progressColor: Color,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        LinearProgressIndicator(
            progress = progress,
            color = progressColor,
            modifier = modifier
                .height(AppTheme.dimens.small)
                .fillMaxWidth()
                .clip(RoundedCornerShape(AppTheme.dimens.normal))
                .background(backgroundColor),
            trackColor = backgroundColor
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(progressColor)
        ) {
            Text(
                modifier = Modifier.padding(start = AppTheme.dimens.small, end = AppTheme.dimens.small),
                text = "${progress.percent().roundTo(2)} %",
                style = MaterialTheme.typography.labelSmall,
                color = backgroundColor,
                fontSize = 10.sp
            )
        }
    }
}

@Preview
@Composable
fun ProgressPercentPreview() {
    MoneyMateTheme {
        ProgressPercent(
            progress = 0.5f,
            progressColor = Color(ExtraColor.Green),
        )
    }
}

// TODO: custom progress indicator with percentage text
/*@Preview
@Composable
fun showProgress(
    score: Int = 100
) {
    val gradient = Brush.linearGradient(
        listOf(
            Color(0xFFF95075),
            Color(0xFFBE6BE5)
        )
    )

    val progressFactor by remember(score) {
        mutableFloatStateOf(score * 0.005f)
    }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(45.dp)
            .border(
                width = 4.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                ),
                shape = RoundedCornerShape(50.dp)
            )
            .clip(
                RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomEndPercent = 50,
                    bottomStartPercent = 50
                )
            )
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = { },
            modifier = Modifier
                .fillMaxWidth(progressFactor)
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {


            Text(
                text = (score * 10).toString(),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(23.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(7.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}*/
