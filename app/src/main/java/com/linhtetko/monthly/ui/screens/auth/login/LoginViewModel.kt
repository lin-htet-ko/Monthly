package com.linhtetko.monthly.ui.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linhtetko.monthly.data.local.pref.PreferenceManager
import com.linhtetko.monthly.data.repositories.AuthRepository
import com.linhtetko.monthly.ui.locale.repository.LanguageRepository
import com.linhtetko.monthly.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val preferenceManager: PreferenceManager,
    private val languageRepository: LanguageRepository
) : ViewModel() {

    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var state by mutableStateOf(ScreenState<Boolean>())

    val languageBundle = languageRepository.currentLanguage

    private val stateHandler = state.handle {
        if (it.success == true) {
            viewModelScope.launch(Dispatchers.IO) {
                preferenceManager.putBool(PreferenceManager.KEY_IS_LOGIN, true)
            }
        }
        state = it
    }

    fun login() {
        if (email.isNotEmpty() && password.isNotEmpty())
            authRepo.login(email, password, stateHandler)
        else
            state = state.copy(error = "Email or Password is required")
    }
}