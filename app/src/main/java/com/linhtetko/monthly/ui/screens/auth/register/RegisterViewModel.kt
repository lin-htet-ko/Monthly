package com.linhtetko.monthly.ui.screens.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.linhtetko.monthly.data.model.UserModel
import com.linhtetko.monthly.data.repositories.AuthRepository
import com.linhtetko.monthly.data.repositories.UserRepository
import com.linhtetko.monthly.ui.locale.repository.LanguageRepository
import com.linhtetko.monthly.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val userRepo: UserRepository,
    private val languageRepository: LanguageRepository
) : ViewModel() {

    val language = languageRepository.currentLanguage

    var name by mutableStateOf("")

    var email by mutableStateOf("")

    var phone by mutableStateOf("")

    var password by mutableStateOf("")

    var image by mutableStateOf<File?>(null)

    var state by mutableStateOf(ScreenState<Boolean>())

    var userState by mutableStateOf(ScreenState<UserModel>())

    private val userStateHandler = userState.handle {
        if (it.success != null) {
            userState = it
        }
        userState = it
    }

    private val stateHandler = state.handle {
        if (it.success == true) {
            userRepo.getUser(state = userStateHandler)
        }
        state = it
    }

    fun register() {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            val user = UserModel(
                name = name,
                email = email,
                phone = phone,
                password = password
            )
            if (image != null)
                authRepo.register(userModel = user, image = image!!, state = stateHandler)
        } else {
            state = state.copy(error = "Email or Password is required")
        }
    }
}