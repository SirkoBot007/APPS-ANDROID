package com.sirko007.dayflow.domain

import androidx.compose.ui.graphics.Color

enum class Priority(val label: String, val color: Color) {
    LOW("Baja", Color(0xFF22C55E)),
    MEDIUM("Media", Color(0xFFF59E0B)),
    HIGH("Alta", Color(0xFFEF4444));

    companion object {
        fun fromName(name: String): Priority =
            entries.firstOrNull { it.name == name } ?: MEDIUM
    }
}

/** Filters shown as chips on the home screen. */
enum class TaskFilter(val label: String) {
    ALL("Todas"),
    TODAY("Hoy"),
    PENDING("Pendientes"),
    DONE("Hechas")
}
