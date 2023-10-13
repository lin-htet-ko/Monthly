package com.linhtetko.monthly.ui.screens.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.linhtetko.monthly.data.local.pref.PreferenceManager
import com.linhtetko.monthly.data.model.ItemModel
import com.linhtetko.monthly.data.model.UserModel
import com.linhtetko.monthly.data.repositories.ItemRepository
import com.linhtetko.monthly.data.repositories.UserRepository
import com.linhtetko.monthly.ui.locale.repository.LanguageRepository
import com.linhtetko.monthly.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateItemViewModel @Inject constructor(
    private val itemRepo: ItemRepository,
    private val userRepository: UserRepository,
    private val preferenceManager: PreferenceManager,
    private val languageRepository: LanguageRepository
) : ViewModel() {

    val language = languageRepository.currentLanguage
    
    var label by mutableStateOf("")
    var price by mutableStateOf("")
    private var uid by mutableStateOf("")

    private var user by mutableStateOf<UserModel?>(null)
    private var userState by mutableStateOf(ScreenState<UserModel>())
    private val userStateHandler = userState.handle {
        userState = it
        user = it.success
    }

    init {
        uid = preferenceManager.getString(PreferenceManager.KEY_UID)

        userRepository.getUser(state = userStateHandler)
    }

    var state by mutableStateOf(ScreenState<Boolean>())

    private val stateHandler = state.handle {
        state = it
    }

    fun createItem(path: String){
        if (user != null) {
            val time = System.currentTimeMillis()
            val item = ItemModel(
                id = time,
                label = label,
                price = price.toLong(),
                userId = uid,
                userName = user?.name ?: "",
                createAt = time,
                type = path
            )
            itemRepo.postItem(path, item, stateHandler)
        }
    }
}