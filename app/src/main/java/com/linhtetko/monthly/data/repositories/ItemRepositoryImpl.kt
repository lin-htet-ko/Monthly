package com.linhtetko.monthly.data.repositories

import com.linhtetko.monthly.data.local.pref.PreferenceManager
import com.linhtetko.monthly.data.model.DashboardModel
import com.linhtetko.monthly.data.model.ItemModel
import com.linhtetko.monthly.data.network.ApiConsumer
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val api: ApiConsumer,
    private val pref: PreferenceManager
) : ItemRepository {

    override fun getItems(state: ApiConsumer.State<List<ItemModel>>) {
        api.getItems(state)
    }

    override fun getGeneralItems(state: ApiConsumer.State<List<ItemModel>>) {
        api.getGeneralItems(state)
    }

    override fun getUserItems(uid: String, state: ApiConsumer.State<List<ItemModel>>) {
        if (uid.isNotEmpty())
            api.getUserItems(uid, state)
    }

    override fun getDashboard(state: ApiConsumer.State<DashboardModel>) {
        api.getDashboard(state)
    }

    override fun postItem(path: String, item: ItemModel, state: ApiConsumer.State<Boolean>) {
        api.postItem(path, item, state)
    }
}