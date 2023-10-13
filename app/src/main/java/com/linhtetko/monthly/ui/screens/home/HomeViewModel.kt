package com.linhtetko.monthly.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.linhtetko.monthly.data.model.DashboardModel
import com.linhtetko.monthly.data.model.ItemModel
import com.linhtetko.monthly.data.model.UserModel
import com.linhtetko.monthly.data.repositories.AuthRepository
import com.linhtetko.monthly.data.repositories.ItemRepository
import com.linhtetko.monthly.data.repositories.UserRepository
import com.linhtetko.monthly.ui.locale.repository.LanguageRepository
import com.linhtetko.monthly.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemRepo: ItemRepository,
    private val userRepo: UserRepository,
    private val authRepo: AuthRepository,
    private val languageRepository: LanguageRepository
) : ViewModel() {

    var isBoughtItemExpand by mutableStateOf(false)
    var isExpand by mutableStateOf(false)

    val languageBundle = languageRepository.currentLanguage

    var itemsState by mutableStateOf(ScreenState<List<ItemModel>>())
        private set

    private val itemsStateHandler = itemsState.handle {
        itemsState = it
    }

    fun getItems() {
        itemRepo.getItems(itemsStateHandler)
    }

    var generalItemsState by mutableStateOf(ScreenState<List<ItemModel>>())
        private set

    private val generalItemsStateHandler = generalItemsState.handle {
        generalItemsState = it
    }

    fun getGeneralItems() {
        itemRepo.getGeneralItems(generalItemsStateHandler)
    }

    var flatMatesState by mutableStateOf(ScreenState<List<UserModel>>())
        private set

    private val flatmatesStateHandler = flatMatesState.handle {
        flatMatesState = it
    }

    fun getFlatmates() {
        userRepo.getUsers(flatmatesStateHandler)
    }

    var dashboardState by mutableStateOf(ScreenState<DashboardModel>())

    private val dashboardStateHandler = dashboardState.handle {
        dashboardState = it
    }

    fun getDashboard() {
        itemRepo.getDashboard(dashboardStateHandler)
    }

    var isLogout by mutableStateOf(false)
        private set

    fun logout() {
        authRepo.logout()
        isLogout = true
    }

    fun onTapExpandable() {
        isExpand = !isExpand
    }

    fun onTapBoughtItemExpand() {
        isBoughtItemExpand = !isBoughtItemExpand
    }
}