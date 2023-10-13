package com.linhtetko.monthly.data.repositories

import com.linhtetko.monthly.data.model.UserModel
import com.linhtetko.monthly.data.network.ApiConsumer
import com.linhtetko.monthly.utils.ScreenState
import java.io.File

interface AuthRepository {

    fun register(userModel: UserModel, image: File, state: ApiConsumer.State<Boolean>)

    fun login(email: String, password: String, state: ApiConsumer.State<Boolean>)

    fun logout()
}