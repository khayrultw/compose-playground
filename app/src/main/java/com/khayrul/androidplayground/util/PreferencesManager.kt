package com.khayrul.androidplayground.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.khayrul.androidplayground.core.AppSettings
import com.khayrul.androidplayground.core.Constants

class PreferencesManager private constructor(context: Context){

    private var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        var preferencesManager: PreferencesManager? = null

        fun getInstance(context: Context): PreferencesManager {
            if(preferencesManager == null) {
                preferencesManager = PreferencesManager(context)
                return preferencesManager as PreferencesManager
            }
            return preferencesManager as PreferencesManager
        }
    }

    fun enableAutoReplyService() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(AppSettings.AUTO_REPLY_SERVICE_ENABLED, true)
        editor.apply()
    }

    fun disableAutoReplyService() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(AppSettings.AUTO_REPLY_SERVICE_ENABLED, false)
        editor.apply()
    }

    fun isAutoReplyServiceEnabled(): Boolean {
        return sharedPreferences.getBoolean(AppSettings.AUTO_REPLY_SERVICE_ENABLED, false)
    }
}