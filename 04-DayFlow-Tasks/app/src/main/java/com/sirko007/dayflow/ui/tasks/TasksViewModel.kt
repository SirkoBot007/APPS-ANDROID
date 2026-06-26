package com.sirko007.dayflow.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirko007.dayflow.data.local.TaskEntity
import com.sirko007.dayflow.data.repository.TaskRepository
import com.sirko007.dayflow.domain.TaskFilter
import com.sirko007.dayflow.ui.isToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TasksUiState(
    val tasks: List<TaskEntity> = emptyList(),
    val filter: TaskFilter = TaskFilter.ALL,
    val pendingCount: Int = 0,
    val doneCount: Int = 0
)

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val selectedFilter = MutableStateFlow(TaskFilter.ALL)

    val uiState: StateFlow<TasksUiState> =
        combine(repository.observeTasks(), selectedFilter) { tasks, filter ->
            TasksUiState(
                tasks = tasks.applyFilter(filter),
                filter = filter,
                pendingCount = tasks.count { !it.isDone },
                doneCount = tasks.count { it.isDone }
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), TasksUiState())

    fun setFilter(filter: TaskFilter) {
        selectedFilter.value = filter
    }

    fun toggleDone(task: TaskEntity) = viewModelScope.launch {
        repository.setDone(task.id, !task.isDone)
    }

    fun delete(task: TaskEntity) = viewModelScope.launch {
        repository.deleteTask(task)
    }

    private fun List<TaskEntity>.applyFilter(filter: TaskFilter): List<TaskEntity> = when (filter) {
        TaskFilter.ALL -> this
        TaskFilter.TODAY -> filter { it.dueDate?.isToday() == true }
        TaskFilter.PENDING -> filter { !it.isDone }
        TaskFilter.DONE -> filter { it.isDone }
    }
}
