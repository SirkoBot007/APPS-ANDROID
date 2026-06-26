package com.sirko007.smartnotes.di

import android.content.Context
import androidx.room.Room
import com.sirko007.smartnotes.BuildConfig
import com.sirko007.smartnotes.data.local.NoteDao
import com.sirko007.smartnotes.data.local.SmartNotesDatabase
import com.sirko007.smartnotes.data.remote.AnthropicApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Hilt module that wires up the singletons the app needs:
 * Room database + DAO, and the Retrofit client for the Anthropic API.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SmartNotesDatabase =
        Room.databaseBuilder(context, SmartNotesDatabase::class.java, "smartnotes.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideNoteDao(db: SmartNotesDatabase): NoteDao = db.noteDao()

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
            else HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                // Inject the API key + required headers on every request.
                val request = chain.request().newBuilder()
                    .addHeader("x-api-key", BuildConfig.ANTHROPIC_API_KEY)
                    .addHeader("anthropic-version", "2023-06-01")
                    .addHeader("content-type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideAnthropicApi(client: OkHttpClient): AnthropicApi =
        Retrofit.Builder()
            .baseUrl("https://api.anthropic.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
            .create(AnthropicApi::class.java)
}
