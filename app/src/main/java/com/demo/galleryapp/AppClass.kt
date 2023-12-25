package com.demo.galleryapp

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

class AppClass : Application() {
    lateinit var mainViewModel: MainViewModel
    override fun onCreate() {

        super.onCreate()
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mainViewModel = MainViewModel(this)
        }
    }
}