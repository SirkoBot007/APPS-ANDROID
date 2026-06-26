package com.sirko007.dayflow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class DayFlowDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
