package com.sirko007.fintrack.ui

import java.text.NumberFormat
import java.util.Locale

private val currencyFormat: NumberFormat =
    NumberFormat.getCurrencyInstance(Locale.US)

/** Formats a Double as currency, e.g. 1234.5 -> "$1,234.50". */
fun Double.asCurrency(): String = currencyFormat.format(this)
