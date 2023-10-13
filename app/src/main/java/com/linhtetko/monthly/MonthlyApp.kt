package com.linhtetko.monthly

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MonthlyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Firebase.initialize(this)
    }
}