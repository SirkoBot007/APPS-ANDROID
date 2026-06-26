package com.sirko007.fintrack.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.sirko007.fintrack.ui.home.CategorySlice

/**
 * A donut chart drawn entirely with Compose Canvas — no third-party chart library.
 * Each [CategorySlice] becomes an arc sized to its fraction of total expenses.
 */
@Composable
fun CategoryDonutChart(
    slices: List<CategorySlice>,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 60f
) {
    Box(modifier = modifier.size(180.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(180.dp)) {
            val stroke = Stroke(width = strokeWidth)
            val arcSize = Size(size.width - strokeWidth, size.height - strokeWidth)
            val topLeft = androidx.compose.ui.geometry.Offset(strokeWidth / 2, strokeWidth / 2)

            var startAngle = -90f
            slices.forEach { slice ->
                val sweep = slice.fraction * 360f
                drawArc(
                    color = slice.category.color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = stroke
                )
                startAngle += sweep
            }
        }
        val biggest = slices.maxByOrNull { it.fraction }
        if (biggest != null) {
            Text(
                text = "${(biggest.fraction * 100).toInt()}%\n${biggest.category.label}",
                style = MaterialTheme.typography.labelMedium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
