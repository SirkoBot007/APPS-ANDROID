package com.sirko007.smartnotes.ui.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirko007.smartnotes.data.local.NoteEntity
import com.sirko007.smartnotes.data.repository.AiAction
import com.sirko007.smartnotes.data.repository.AiRepository
import com.sirko007.smartnotes.data.repository.AiResult
import com.sirko007.smartnotes.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/** UI state for the editor screen — immutable snapshot the Composable renders. */
data class EditorUiState(
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val isAiLoading: Boolean = false,
    val aiSuggestion: String? = null,
    val error: String? = null,
    val saved: Boolean = false
)

@HiltViewModel
class NoteEditorViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    private val aiRepository: AiRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteId: Long = savedStateHandle.get<Long>("noteId") ?: 0L

    private val _state = MutableStateFlow(EditorUiState())
    val state: StateFlow<EditorUiState> = _state.asStateFlow()

    init {
        if (noteId != 0L) loadNote(noteId)
    }

    private fun loadNote(id: Long) = viewModelScope.launch {
        notesRepository.getNote(id)?.let { note ->
            _state.update { it.copy(id = note.id, title = note.title, content = note.content) }
        }
    }

    fun onTitleChange(value: String) = _state.update { it.copy(title = value) }
    fun onContentChange(value: String) = _state.update { it.copy(content = value) }
    fun dismissError() = _state.update { it.copy(error = null) }
    fun dismissSuggestion() = _state.update { it.copy(aiSuggestion = null) }

    /** Replace the note body with the AI suggestion. */
    fun applySuggestion() = _state.update {
        it.copy(content = it.aiSuggestion ?: it.content, aiSuggestion = null)
    }

    fun runAi(action: AiAction) = viewModelScope.launch {
        _state.update { it.copy(isAiLoading = true, error = null, aiSuggestion = null) }
        when (val result = aiRepository.run(action, _state.value.content)) {
            is AiResult.Success -> _state.update {
                it.copy(isAiLoading = false, aiSuggestion = result.text)
            }
            is AiResult.Error -> _state.update {
                it.copy(isAiLoading = false, error = result.message)
            }
        }
    }

    fun save() = viewModelScope.launch {
        val s = _state.value
        if (s.title.isBlank() && s.content.isBlank()) {
            _state.update { it.copy(error = "No hay nada que guardar.") }
            return@launch
        }
        val id = notesRepository.saveNote(
            NoteEntity(
                id = s.id,
                title = s.title.ifBlank { "Sin título" },
                content = s.content,
                updatedAt = System.currentTimeMillis()
            )
        )
        _state.update { it.copy(id = id, saved = true) }
    }
}
