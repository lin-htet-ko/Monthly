package com.linhtetko.monthly.data.repositories

import com.linhtetko.monthly.data.model.DashboardModel
import com.linhtetko.monthly.data.model.ItemModel
import com.linhtetko.monthly.data.network.ApiConsumer

interface ItemRepository {

    fun getItems(state: ApiConsumer.State<List<ItemModel>>)

    fun getGeneralItems(state: ApiConsumer.State<List<ItemModel>>)

    fun getUserItems(uid: String, state: ApiConsumer.State<List<ItemModel>>)

    fun getDashboard(state: ApiConsumer.State<DashboardModel>)

    fun postItem(path: String, item: ItemModel, state: ApiConsumer.State<Boolean>)
}