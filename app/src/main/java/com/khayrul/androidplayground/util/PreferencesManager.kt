package com.khayrul.androidplayground.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PreferencesManager private constructor(context: Context){

    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    companion object {
        var preferencesManager: PreferencesManager? = null

        fun create(context: Context): PreferencesManager? {
            if(preferencesManager == null) {
                preferencesManager = PreferencesManager(context)
            }
            return preferencesManager
        }
    }
}