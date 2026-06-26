package com.sirko007.cryptopulse.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

/**
 * A minimal 7-day price line ("sparkline"), drawn with Compose Canvas.
 * Normalizes the price series into the available width/height.
 */
@Composable
fun SparklineChart(
    prices: List<Double>,
    color: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 3f
) {
    Canvas(modifier = modifier) {
        if (prices.size < 2) return@Canvas

        val min = prices.min()
        val max = prices.max()
        val range = (max - min).takeIf { it != 0.0 } ?: 1.0

        val stepX = size.width / (prices.size - 1)
        val path = Path()

        prices.forEachIndexed { index, price ->
            val x = stepX * index
            val y = size.height - ((price - min) / range * size.height).toFloat()
            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(width = strokeWidth)
        )
    }
}
