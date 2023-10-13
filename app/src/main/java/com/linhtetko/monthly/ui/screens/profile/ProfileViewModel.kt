package com.linhtetko.monthly.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.linhtetko.monthly.data.model.ItemModel
import com.linhtetko.monthly.data.model.UserModel
import com.linhtetko.monthly.data.repositories.ItemRepository
import com.linhtetko.monthly.data.repositories.UserRepository
import com.linhtetko.monthly.ui.locale.repository.LanguageRepository
import com.linhtetko.monthly.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val itemRepo: ItemRepository,
    private val languageRepository: LanguageRepository
) : ViewModel() {

    var phoneNumber: String? by mutableStateOf(null)
    val language = languageRepository.currentLanguage

    var userState by mutableStateOf(ScreenState<UserModel>())
    private val userStateHandler = userState.handle {
        userState = it
    }

    fun getProfile(uid: String) {
        userRepo.getUser(uid = uid, state = userStateHandler)
    }

    var userItemsState by mutableStateOf(ScreenState<List<ItemModel>>())
    private val userItemsHandler = userItemsState.handle {
        userItemsState = it
    }

    fun getUserItems(uid: String) {
        itemRepo.getUserItems(uid, userItemsHandler)
    }
}