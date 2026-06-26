package com.sirko007.dayflow.di

import android.content.Context
import androidx.room.Room
import com.sirko007.dayflow.data.local.DayFlowDatabase
import com.sirko007.dayflow.data.local.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DayFlowDatabase =
        Room.databaseBuilder(context, DayFlowDatabase::class.java, "dayflow.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideTaskDao(db: DayFlowDatabase): TaskDao = db.taskDao()
}
