package com.sirko007.dayflow.data.repository

import com.sirko007.dayflow.data.local.TaskDao
import com.sirko007.dayflow.data.local.TaskEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val dao: TaskDao
) {
    fun observeTasks(): Flow<List<TaskEntity>> = dao.observeAll()

    suspend fun getTask(id: Long): TaskEntity? = dao.getById(id)

    suspend fun saveTask(task: TaskEntity): Long = dao.upsert(task)

    suspend fun setDone(id: Long, done: Boolean) = dao.setDone(id, done)

    suspend fun deleteTask(task: TaskEntity) = dao.delete(task)
}
