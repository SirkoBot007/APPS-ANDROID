package com.sirko007.cryptopulse.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Amber = Color(0xFFF59E0B)
val Night = Color(0xFF0B1020)
val NightSurface = Color(0xFF151B2E)
val Up = Color(0xFF22C55E)
val Down = Color(0xFFEF4444)

private val DarkColors = darkColorScheme(
    primary = Amber,
    onPrimary = Night,
    background = Night,
    surface = NightSurface,
    onBackground = Color(0xFFE8EAF0),
    onSurface = Color(0xFFE8EAF0)
)

private val AppTypography = Typography(
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 26.sp, lineHeight = 32.sp),
    titleLarge = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp, lineHeight = 24.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 22.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
    labelMedium = TextStyle(fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 16.sp)
)

@Composable
fun CryptoPulseTheme(content: @Composable () -> Unit) {
    // Always dark — fits a finance/crypto dashboard aesthetic.
    MaterialTheme(colorScheme = DarkColors, typography = AppTypography, content = content)
}
