package com.linhtetko.monthly.data.repositories

import com.linhtetko.monthly.data.local.pref.PreferenceManager
import com.linhtetko.monthly.data.model.UserModel
import com.linhtetko.monthly.data.network.ApiConsumer
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: ApiConsumer,
    private val pref: PreferenceManager
) : UserRepository {

    override fun getUsers(state: ApiConsumer.State<List<UserModel>>) {
        api.getFlatmates(state)
    }

    override fun getUser(uid: String?,state: ApiConsumer.State<UserModel>) {
        val id = uid ?: pref.getString(PreferenceManager.KEY_UID)
        if (id.isNotEmpty())
            api.getProfile(id, state)
    }
}