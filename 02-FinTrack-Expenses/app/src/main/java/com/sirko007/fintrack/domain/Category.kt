package com.sirko007.fintrack.domain

import androidx.compose.ui.graphics.Color

/**
 * Spending/earning categories. Each carries a stable color used both in the
 * list and in the donut chart, so the visualization stays consistent.
 */
enum class Category(val label: String, val color: Color, val isIncome: Boolean = false) {
    FOOD("Comida", Color(0xFFEF4444)),
    TRANSPORT("Transporte", Color(0xFF3B82F6)),
    SHOPPING("Compras", Color(0xFFA855F7)),
    BILLS("Facturas", Color(0xFFF59E0B)),
    ENTERTAINMENT("Ocio", Color(0xFFEC4899)),
    HEALTH("Salud", Color(0xFF14B8A6)),
    SALARY("Salario", Color(0xFF22C55E), isIncome = true),
    OTHER("Otros", Color(0xFF64748B));

    companion object {
        fun fromName(name: String): Category =
            entries.firstOrNull { it.name == name } ?: OTHER
    }
}
