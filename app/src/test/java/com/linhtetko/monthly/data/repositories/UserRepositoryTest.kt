package com.linhtetko.monthly.data.repositories

import com.linhtetko.monthly.data.local.pref.PreferenceManager
import com.linhtetko.monthly.data.model.UserModel
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
class UserRepositoryTest {

    @Mock
    lateinit var api: ApiConsumer

    @Mock
    lateinit var usersState: ApiConsumer.State<List<UserModel>>

    @Mock
    lateinit var userState: ApiConsumer.State<UserModel>

    @Mock lateinit var pref: PreferenceManager

    private val errorMessage = FirebaseApiConsumer.GENERAL_ERROR_MESSAGE
    private lateinit var userRepository: UserRepository
    private val users = listOf(
        UserModel(),
        UserModel(),
        UserModel()
    )
    private val user = UserModel()

    companion object {

        lateinit var uid: String

        @JvmStatic
        @BeforeClass
        fun setUpClass() {
            val faker = Faker()
            uid = faker.number.digit()
        }
    }

    @Before
    fun setUp() {
        userRepository = UserRepositoryImpl(api, pref)
    }

    @Test
    fun onSuccess_when_invoke_getUsers() {
        whenever(api.getFlatmates(usersState)).then {
            usersState.onSuccess(users)
        }

        userRepository.getUsers(usersState)

        verify(api).getFlatmates(usersState)
        verify(usersState).onSuccess(users)
    }

    @Test
    fun onFailure_when_invoke_getUsers() {
        whenever(api.getFlatmates(usersState)).then {
            usersState.onFailure(errorMessage)
        }

        userRepository.getUsers(usersState)

        verify(api).getFlatmates(usersState)
        verify(usersState).onFailure(errorMessage)
    }

    @Test
    fun onLoading_when_invoke_getUsers() {
        whenever(api.getFlatmates(usersState)).then {
            usersState.onLoading()
        }

        userRepository.getUsers(usersState)

        verify(api).getFlatmates(usersState)
        verify(usersState).onLoading()
    }

    @Test
    fun onSuccess_when_invoke_getUser() {
        whenever(api.getProfile(uid, userState)).then {
            userState.onSuccess(user)
        }

        userRepository.getUser(uid, userState)

        verify(api).getProfile(uid, userState)
        verify(userState).onSuccess(user)
    }

    @Test
    fun onFailure_when_invoke_getUser() {
        whenever(api.getProfile(uid, userState)).then {
            userState.onFailure(errorMessage)
        }

        userRepository.getUser(uid, userState)

        verify(api).getProfile(uid, userState)
        verify(userState).onFailure(errorMessage)
    }

    @Test
    fun onLoading_when_invoke_getUser() {
        whenever(api.getProfile(uid, userState)).then {
            userState.onLoading()
        }

        userRepository.getUser(uid, userState)

        verify(api).getProfile(uid, userState)
        verify(userState).onLoading()
    }
}