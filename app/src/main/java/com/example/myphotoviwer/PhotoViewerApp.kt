package com.example.myphotoviwer

import android.app.Application
import com.unsplash.pickerandroid.photopicker.UnsplashPhotoPicker
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PhotoViewerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        UnsplashPhotoPicker.init(
            this, // application
            "KVYTmZHuXG3ZLk49Ka2AqNyFqV9FMqlEYGwx4-4LvE4",
            "w5G-hhgBAedoMgq72rIL8FdF1lD-RTc4ES3bPZMUvmM",
            20
        )
    }
}