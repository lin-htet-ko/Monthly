package com.linhtetko.monthly.data.repositories

import com.linhtetko.monthly.data.model.UserModel
import com.linhtetko.monthly.data.network.ApiConsumer
import io.bloco.faker.Faker
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryTest {

    @Mock
    lateinit var api: ApiConsumer

    @Mock
    lateinit var state: ApiConsumer.State<Boolean>

    @Mock
    lateinit var image: File

    private lateinit var authRepository: AuthRepository
    private lateinit var user: UserModel
    private lateinit var faker: Faker
    private lateinit var email: String
    private lateinit var password: String

    @Before
    fun setUp() {
        faker = Faker()
        email = faker.internet.email()
        password = faker.internet.password()

        authRepository = AuthRepositoryImpl(api)

        user = UserModel(
            id = faker.number.digit(),
            name = faker.name.name(),
            email = faker.internet.email(),
            password = faker.internet.password(),
            phone = faker.phoneNumber.phoneNumber(),
            imgPath = faker.avatar.image()
        )
    }

    @Test
    fun onFailure_when_register_with_user_email_or_password_is_Empty() {

        val message = "Email or Password is required"
        val user = UserModel()
        whenever(api.register(user, image, state)).then {
            state.onFailure(message)
        }

        authRepository.register(user, image, state)

        verify(api).register(user, image, state)
        verify(state).onFailure(message)
    }

    @Test
    fun onSuccess_when_register() {
        whenever(api.register(user, image, state)).then {
            state.onSuccess(true)
        }

        authRepository.register(user, image, state)

        verify(api).register(user, image, state)
        verify(state).onSuccess(true)
    }

    @Test
    fun onLoading_when_register() {
        whenever(api.register(user, image, state)).then {
            state.onLoading()
        }

        authRepository.register(user, image, state)

        verify(api).register(user, image, state)
        verify(state).onLoading()
    }

    @Test
    fun onFailure_when_login_with_blank_email_and_password() {
        val message = "Email or Password is required"

        whenever(api.login(email, password, state)).then {
            state.onFailure(message)
        }

        authRepository.login(email, password, state)

        verify(api).login(email, password, state)
        verify(state).onFailure(message)
    }

    @Test
    fun onSuccess_when_login() {
        whenever(api.login(email, password, state)).then {
            state.onSuccess(true)
        }

        authRepository.login(email, password, state)

        verify(api).login(email, password, state)
        verify(state).onSuccess(true)
    }

    @Test
    fun onLoading_when_login() {
        whenever(api.login(email, password, state)).then {
            state.onLoading()
        }

        authRepository.login(email, password, state)

        verify(api).login(email, password, state)
        verify(state).onLoading()
    }
}