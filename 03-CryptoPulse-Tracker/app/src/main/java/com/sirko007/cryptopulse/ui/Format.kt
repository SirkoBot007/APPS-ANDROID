package com.sirko007.cryptopulse.ui

import java.util.Locale

/** Compact price formatting that handles both $60,000 and $0.00004321 ranges. */
fun Double.asPrice(): String = when {
    this >= 1.0 -> "$" + String.format(Locale.US, "%,.2f", this)
    this > 0.0 -> "$" + String.format(Locale.US, "%.6f", this).trimEnd('0').trimEnd('.')
    else -> "$0.00"
}

fun Double.asPercent(): String =
    (if (this >= 0) "+" else "") + String.format(Locale.US, "%.2f", this) + "%"

fun Double.asMarketCap(): String = when {
    this >= 1_000_000_000 -> String.format(Locale.US, "$%.2fB", this / 1_000_000_000)
    this >= 1_000_000 -> String.format(Locale.US, "$%.2fM", this / 1_000_000)
    else -> "$" + String.format(Locale.US, "%,.0f", this)
}
