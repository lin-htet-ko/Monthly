package com.linhtetko.monthly.ui.locale.repository

import com.linhtetko.monthly.ui.locale.language.Language
import com.linhtetko.monthly.ui.locale.language.LanguageBundle
import com.linhtetko.monthly.ui.locale.language.LanguageBundles
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {

    val languages: Flow<List<LanguageBundle>>

    val currentLanguage: Flow<LanguageBundle>

    suspend fun updateLanguage(code: String)

}