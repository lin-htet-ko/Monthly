package com.linhtetko.monthly.data.local.pref

import android.content.SharedPreferences
import javax.inject.Inject

class SharePreferenceManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
): PreferenceManager {

    private val edit = sharedPreferences.edit()

    override fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    override fun putBool(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun getBool(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}