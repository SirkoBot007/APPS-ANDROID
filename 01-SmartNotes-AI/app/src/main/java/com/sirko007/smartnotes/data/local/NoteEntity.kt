package com.sirko007.smartnotes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A single note persisted locally with Room.
 */
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val updatedAt: Long = System.currentTimeMillis()
)
