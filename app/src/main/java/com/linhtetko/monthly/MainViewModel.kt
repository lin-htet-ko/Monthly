package com.linhtetko.monthly

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linhtetko.monthly.data.local.pref.PreferenceManager
import com.linhtetko.monthly.data.repositories.AuthRepository
import com.linhtetko.monthly.ui.locale.language.LanguageType
import com.linhtetko.monthly.ui.locale.repository.LanguageRepository
import com.linhtetko.monthly.ui.screens.Screen
import com.linhtetko.monthly.ui.theme.ColorItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val languageRepository: LanguageRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    val colors = ColorItem.colors
    var currentSelectedColor by mutableStateOf(preferenceManager.getString(PreferenceManager.KEY_APP_COLOR))

    fun onSelectColor(colorItem: ColorItem) {
        preferenceManager.putString(PreferenceManager.KEY_APP_COLOR, colorItem.code)
        currentSelectedColor = colorItem.code
    }

    fun getRootDestination(): String {
        return if (preferenceManager.getString(PreferenceManager.KEY_UID)
                .isNotEmpty()
        ) Screen.Main.route else Screen.Register.route
    }

    var isLogout by mutableStateOf(false)
        private set

    val languages = languageRepository.languages

    val selectedLanguage = languageRepository.currentLanguage

    fun saveLanguage(type: LanguageType) {
        viewModelScope.launch {
            languageRepository.updateLanguage(type.code)
        }
    }

    var showLanguageDialog by mutableStateOf(false)

    fun logout() {
        authRepository.logout()
        isLogout = true
    }
}