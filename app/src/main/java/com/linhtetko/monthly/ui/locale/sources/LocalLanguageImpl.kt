package com.linhtetko.monthly.ui.locale.sources

import android.content.Context
import com.linhtetko.monthly.R
import com.linhtetko.monthly.ui.locale.language.Language
import com.linhtetko.monthly.ui.locale.language.LanguageBundle
import com.linhtetko.monthly.ui.locale.language.LanguageBundles
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LocalLanguageImpl @Inject constructor(@ApplicationContext private val context: Context): LocalLanguage {

    override fun getLanguage(): LanguageBundles {
        return context.resources.openRawResource(R.raw.localizations).use {
            val text = it.bufferedReader().readText()

            Json.decodeFromString(text)
        }
    }
}