package com.linhtetko.monthly.data.local.pref

interface PreferenceManager {

    companion object {
        const val KEY_APP_COLOR: String = "key-app-color"
        const val PREFERENCES = "preferences"
        const val KEY_UID = "key-uid"
        const val KEY_IS_LOGIN = "is-login"
        const val KEY_APP_LANGUAGE = "app_language"
    }

    fun putString(key: String, value: String)

    fun getString(key: String): String
    fun putBool(key: String, b: Boolean)
    fun getBool(key: String): Boolean
    fun clear()

}