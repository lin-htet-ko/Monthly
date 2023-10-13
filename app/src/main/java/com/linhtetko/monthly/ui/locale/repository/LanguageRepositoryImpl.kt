package com.linhtetko.monthly.ui.locale.repository

import com.linhtetko.monthly.data.local.pref.PreferenceManager
import com.linhtetko.monthly.ui.locale.language.Language
import com.linhtetko.monthly.ui.locale.language.LanguageBundle
import com.linhtetko.monthly.ui.locale.language.LanguageBundles
import com.linhtetko.monthly.ui.locale.sources.LocalLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val local: LocalLanguage
) : LanguageRepository {

    private var _cacheLanguages: List<LanguageBundle>? = null
    private var _cacheCurrentLanguage: LanguageBundle? = null

    private val _languages: MutableStateFlow<List<LanguageBundle>> by lazy {
        _cacheLanguages = local.getLanguage().bundles

        MutableStateFlow(_cacheLanguages!!)
    }

    private val _currentLanguage: MutableStateFlow<LanguageBundle> by lazy {
        val code = preferenceManager.getString(PreferenceManager.KEY_APP_LANGUAGE)
            .ifEmpty { Language.ENGLISH }
        _cacheCurrentLanguage = _cacheLanguages?.first{ it.type.code == code}

        MutableStateFlow(_cacheCurrentLanguage!!)
    }

    override val languages: Flow<List<LanguageBundle>> = _languages

    override val currentLanguage: Flow<LanguageBundle> = _currentLanguage

    override suspend fun updateLanguage(code: String) {
        _currentLanguage.value = (local.getLanguage().mapper(code) ?: _cacheLanguages?.first { it.type.code == code })!!

        preferenceManager.putString(PreferenceManager.KEY_APP_LANGUAGE, code)
    }
}