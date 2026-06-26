package com.sirko007.dayflow.ui

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private val dayFormat = SimpleDateFormat("EEE, d MMM", Locale.getDefault())

fun Long.asDueDate(): String = dayFormat.format(Date(this))

/** True if the given epoch-millis falls on the current calendar day. */
fun Long.isToday(): Boolean {
    val now = Calendar.getInstance()
    val that = Calendar.getInstance().apply { timeInMillis = this@isToday }
    return now.get(Calendar.YEAR) == that.get(Calendar.YEAR) &&
        now.get(Calendar.DAY_OF_YEAR) == that.get(Calendar.DAY_OF_YEAR)
}

/** Epoch-millis for the end of today — used as the default due date. */
fun endOfToday(): Long = Calendar.getInstance().apply {
    set(Calendar.HOUR_OF_DAY, 23)
    set(Calendar.MINUTE, 59)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}.timeInMillis
