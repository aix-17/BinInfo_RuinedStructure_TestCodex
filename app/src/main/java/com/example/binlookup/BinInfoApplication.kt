package com.example.binlookup

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BinInfoApplication : Application() {
    
    companion object {
        private const val TAG = "BinInfoApplication"
    }

    override fun onCreate() {
        super.onCreate()

    }



    override fun onLowMemory() {
        super.onLowMemory()
        Log.w(TAG, "Low memory warning received")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        when (level) {
            TRIM_MEMORY_COMPLETE -> Log.w(TAG, "Memory trim level: COMPLETE")
            TRIM_MEMORY_MODERATE -> Log.w(TAG, "Memory trim level: MODERATE")
            TRIM_MEMORY_RUNNING_CRITICAL -> Log.w(TAG, "Memory trim level: RUNNING_CRITICAL")
        }
    }
} 