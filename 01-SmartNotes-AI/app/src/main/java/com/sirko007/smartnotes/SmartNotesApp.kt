package com.sirko007.smartnotes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application entry point. @HiltAndroidApp triggers Hilt's code generation,
 * creating the application-level dependency container.
 */
@HiltAndroidApp
class SmartNotesApp : Application()
