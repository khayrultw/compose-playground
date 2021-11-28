package com.khayrul.androidplayground.core.preference

import android.content.Context
import androidx.preference.PreferenceManager
import com.khayrul.androidplayground.core.constants.AppSettings

class PreferencesManager private constructor(context: Context){

    private var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        var preferencesManager: PreferencesManager? = null

        fun getInstance(context: Context): PreferencesManager {
            if(preferencesManager == null) {
                preferencesManager = PreferencesManager(context)
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