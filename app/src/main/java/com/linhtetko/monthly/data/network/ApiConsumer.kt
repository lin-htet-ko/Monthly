package com.linhtetko.monthly.data.network

import com.linhtetko.monthly.data.model.DashboardModel
import com.linhtetko.monthly.data.model.ItemModel
import com.linhtetko.monthly.data.model.UserModel
import com.linhtetko.monthly.utils.ScreenState
import java.io.File

interface ApiConsumer {

    interface State<T> {
        fun onLoading()
        fun onSuccess(items: T)
        fun onFailure(message: String)
    }

    fun getProfile(id: String, state: State<UserModel>)

    fun getFlatmates(state: State<List<UserModel>>)

    fun getItems(state: State<List<ItemModel>>)

    fun getGeneralItems(state: State<List<ItemModel>>)

    fun getUserItems(uid: String, state: State<List<ItemModel>>)

    fun getDashboard(state: State<DashboardModel>)

    fun postItem(path: String, item: ItemModel, state: State<Boolean>)

    fun postFlatmate(user: UserModel, state: State<Boolean>)

    fun register(user: UserModel, image: File, state: State<Boolean>)

    fun login(email: String, password: String, state: State<Boolean>)

    fun logout()
}