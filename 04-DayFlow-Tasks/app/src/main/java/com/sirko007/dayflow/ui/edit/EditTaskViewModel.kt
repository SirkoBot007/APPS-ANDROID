package com.sirko007.dayflow.ui.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirko007.dayflow.data.local.TaskEntity
import com.sirko007.dayflow.data.repository.TaskRepository
import com.sirko007.dayflow.domain.Priority
import com.sirko007.dayflow.ui.endOfToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditUiState(
    val id: Long = 0,
    val title: String = "",
    val notes: String = "",
    val priority: Priority = Priority.MEDIUM,
    val hasDueDate: Boolean = false,
    val dueDate: Long? = null,
    val isDone: Boolean = false,
    val error: String? = null,
    val saved: Boolean = false
)

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId: Long = savedStateHandle.get<Long>("taskId") ?: 0L

    private val _state = MutableStateFlow(EditUiState())
    val state: StateFlow<EditUiState> = _state.asStateFlow()

    init {
        if (taskId != 0L) viewModelScope.launch {
            repository.getTask(taskId)?.let { task ->
                _state.update {
                    it.copy(
                        id = task.id,
                        title = task.title,
                        notes = task.notes,
                        priority = Priority.fromName(task.priority),
                        hasDueDate = task.dueDate != null,
                        dueDate = task.dueDate,
                        isDone = task.isDone
                    )
                }
            }
        }
    }

    fun onTitleChange(v: String) = _state.update { it.copy(title = v) }
    fun onNotesChange(v: String) = _state.update { it.copy(notes = v) }
    fun onPriorityChange(p: Priority) = _state.update { it.copy(priority = p) }
    fun dismissError() = _state.update { it.copy(error = null) }

    fun onDueDateToggle(enabled: Boolean) = _state.update {
        it.copy(hasDueDate = enabled, dueDate = if (enabled) (it.dueDate ?: endOfToday()) else null)
    }

    fun save() {
        val s = _state.value
        if (s.title.isBlank()) {
            _state.update { it.copy(error = "Escribe un título.") }
            return
        }
        viewModelScope.launch {
            repository.saveTask(
                TaskEntity(
                    id = s.id,
                    title = s.title.trim(),
                    notes = s.notes.trim(),
                    priority = s.priority.name,
                    isDone = s.isDone,
                    dueDate = if (s.hasDueDate) s.dueDate else null
                )
            )
            _state.update { it.copy(saved = true) }
        }
    }
}
