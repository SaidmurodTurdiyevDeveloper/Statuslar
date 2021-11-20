package com.example.statuslar.app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    private var instanse: Context? = null
    override fun onCreate() {
        super.onCreate()
        instanse = this
    }
}