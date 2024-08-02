package com.khtw.encfile

import android.app.Application
import com.google.android.material.color.DynamicColors


class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    companion object {
        init {
            System.loadLibrary("encfile")
        }
    }
}
