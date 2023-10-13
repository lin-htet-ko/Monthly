package com.linhtetko.monthly.data.repositories

import com.linhtetko.monthly.data.model.UserModel
import com.linhtetko.monthly.data.network.ApiConsumer
import com.linhtetko.monthly.utils.ScreenState
import java.io.File
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiConsumer
) : AuthRepository {

    override fun register(userModel: UserModel, image: File, state: ApiConsumer.State<Boolean>) {
        api.register(userModel, image, state)
    }

    override fun login(email: String, password: String, state: ApiConsumer.State<Boolean>) {
        api.login(email, password, state)
    }

    override fun logout() {
        api.logout()
    }
}