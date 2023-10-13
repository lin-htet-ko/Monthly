package com.linhtetko.monthly.data.repositories

import com.linhtetko.monthly.data.local.pref.PreferenceManager
import com.linhtetko.monthly.data.model.DashboardModel
import com.linhtetko.monthly.data.model.ItemModel
import com.linhtetko.monthly.data.network.ApiConsumer
import com.linhtetko.monthly.data.network.FirebaseApiConsumer
import io.bloco.faker.Faker
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class ItemRepositoryTest {

    @Mock
    lateinit var api: ApiConsumer

    @Mock
    lateinit var itemsState: ApiConsumer.State<List<ItemModel>>

    @Mock
    lateinit var dashboardState: ApiConsumer.State<DashboardModel>

    @Mock
    lateinit var postItemState: ApiConsumer.State<Boolean>

    @Mock
    lateinit var pref: PreferenceManager

    private lateinit var itemRepository: ItemRepository
    private val items = listOf(
        ItemModel(),
        ItemModel(),
        ItemModel()
    )
    private val errorMessage = FirebaseApiConsumer.GENERAL_ERROR_MESSAGE
    private lateinit var faker: Faker
    private lateinit var uid: String

    companion object {
        private lateinit var dashboardModel: DashboardModel

        @JvmStatic
        @BeforeClass
        fun setUpClass() {
            dashboardModel = DashboardModel(total = 10000, each = 1000.0)
        }
    }

    @Before
    fun setUp() {
        itemRepository = ItemRepositoryImpl(api, pref)

        faker = Faker()
        uid = faker.number.digit()

    }

    private fun getId(){
        whenever(pref.getString(PreferenceManager.KEY_UID)).thenReturn(uid)
    }

    @Test
    fun onSuccess_when_invoke_getItems() {

        whenever(api.getItems(itemsState)).then {
            itemsState.onSuccess(items)
        }

        itemRepository.getItems(itemsState)

        verify(api).getItems(itemsState)
        verify(itemsState).onSuccess(items)
    }

    @Test
    fun onFailure_when_invoke_getItems() {
        whenever(api.getItems(itemsState)).then {
            itemsState.onFailure(errorMessage)
        }

        itemRepository.getItems(itemsState)

        verify(api).getItems(itemsState)
        verify(itemsState).onFailure(errorMessage)
    }

    @Test
    fun onLoading_when_invoke_getItems() {
        whenever(api.getItems(itemsState)).then {
            itemsState.onLoading()
        }

        itemRepository.getItems(itemsState)

        verify(api).getItems(itemsState)
        verify(itemsState).onLoading()
    }

    @Test
    fun onSuccess_when_invoke_getUserItems() {
        getId()
        whenever(api.getUserItems(uid, itemsState)).then {
            itemsState.onSuccess(items)
        }

        itemRepository.getUserItems(uid, itemsState)

        verify(api).getUserItems(uid, itemsState)
        verify(itemsState).onSuccess(items)
    }

    @Test
    fun onFailure_when_invoke_getUserItems() {
        getId()
        whenever(api.getUserItems(uid, itemsState)).then {
            itemsState.onFailure(errorMessage)
        }

        itemRepository.getUserItems(uid, itemsState)

        verify(api).getUserItems(uid, itemsState)
        verify(itemsState).onFailure(errorMessage)
    }

    @Test
    fun onLoading_when_invoke_getUserItems() {
        getId()
        whenever(api.getUserItems(uid, itemsState)).then {
            itemsState.onLoading()
        }

        itemRepository.getUserItems(uid, itemsState)

        verify(api).getUserItems(uid, itemsState)
        verify(itemsState).onLoading()
    }

    @Test
    fun onSuccess_when_invoke_getDashboard() {
        whenever(api.getDashboard(dashboardState)).then {
            dashboardState.onSuccess(dashboardModel)
        }

        itemRepository.getDashboard(dashboardState)

        verify(api).getDashboard(dashboardState)
        verify(dashboardState).onSuccess(dashboardModel)
    }

    @Test
    fun onFailure_when_invoke_getDashboard() {
        whenever(api.getDashboard(dashboardState)).then {
            dashboardState.onFailure(errorMessage)
        }

        itemRepository.getDashboard(dashboardState)

        verify(api).getDashboard(dashboardState)
        verify(dashboardState).onFailure(errorMessage)
    }

    @Test
    fun onLoading_when_invoke_getDashboard() {
        whenever(api.getDashboard(dashboardState)).then {
            dashboardState.onLoading()
        }

        itemRepository.getDashboard(dashboardState)

        verify(api).getDashboard(dashboardState)
        verify(dashboardState).onLoading()
    }

    @Test
    fun onSuccess_when_invoke_postItem() {
        getId()
        val item = items[0]
        whenever(api.postItem(uid, item, postItemState)).then {
            postItemState.onSuccess(true)
        }

        itemRepository.postItem(uid, item, postItemState)

        verify(api).postItem(uid, item, postItemState)
        verify(postItemState).onSuccess(true)
    }

    @Test
    fun onFailure_when_invoke_postItem() {
        getId()
        val item = items[0]
        whenever(api.postItem(uid, item, postItemState)).then {
            postItemState.onFailure(errorMessage)
        }

        itemRepository.postItem(uid, item, postItemState)

        verify(api).postItem(uid, item, postItemState)
        verify(postItemState).onFailure(errorMessage)
    }

    @Test
    fun onLoading_when_invoke_postItem() {
        getId()
        val item = items[0]
        whenever(api.postItem(uid, item, postItemState)).then {
            postItemState.onLoading()
        }

        itemRepository.postItem(uid, item, postItemState)

        verify(api).postItem(uid, item, postItemState)
        verify(postItemState).onLoading()
    }
}