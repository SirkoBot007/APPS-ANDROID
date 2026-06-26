package com.sirko007.smartnotes.data.repository

import com.sirko007.smartnotes.data.local.NoteDao
import com.sirko007.smartnotes.data.local.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single source of truth for notes. The UI never touches the DAO directly —
 * it goes through this repository (classic MVVM/Clean separation).
 */
@Singleton
class NotesRepository @Inject constructor(
    private val dao: NoteDao
) {
    fun observeNotes(): Flow<List<NoteEntity>> = dao.observeAll()

    suspend fun getNote(id: Long): NoteEntity? = dao.getById(id)

    suspend fun saveNote(note: NoteEntity): Long = dao.upsert(note)

    suspend fun deleteNote(note: NoteEntity) = dao.delete(note)
}
