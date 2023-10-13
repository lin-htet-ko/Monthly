package com.linhtetko.monthly.data.repositories

import com.linhtetko.monthly.data.model.UserModel
import com.linhtetko.monthly.data.network.ApiConsumer

interface UserRepository {

    fun getUsers(state: ApiConsumer.State<List<UserModel>>)

    fun getUser(uid: String? = null,state: ApiConsumer.State<UserModel>)

}