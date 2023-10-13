package com.linhtetko.monthly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.linhtetko.monthly.ui.screens.Screen
import com.linhtetko.monthly.ui.screens.auth.login.LoginScreen
import com.linhtetko.monthly.ui.screens.auth.login.LoginViewModel
import com.linhtetko.monthly.ui.screens.auth.register.RegisterScreen
import com.linhtetko.monthly.ui.screens.auth.register.RegisterViewModel
import com.linhtetko.monthly.ui.screens.create.CreateGeneralItemScreen
import com.linhtetko.monthly.ui.screens.create.CreateItemViewModel
import com.linhtetko.monthly.ui.screens.home.HomeScreen
import com.linhtetko.monthly.ui.screens.home.HomeViewModel
import com.linhtetko.monthly.ui.screens.profile.ProfileScreen
import com.linhtetko.monthly.ui.screens.profile.ProfileViewModel
import com.linhtetko.monthly.ui.screens.setting.SettingScreen
import com.linhtetko.monthly.ui.screens.splash.SplashScreen
import com.linhtetko.monthly.ui.theme.MonthlyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootDestination = mainViewModel.getRootDestination()

        setContent {
            MonthlyTheme(colorCode = mainViewModel.currentSelectedColor) {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    MonthlyApp(
                        navController = navController,
                        rootDestination = rootDestination,
                        viewModel = mainViewModel
                    )
                }
            }
        }
    }
}


@Composable
fun MonthlyApp(
    navController: NavHostController,
    rootDestination: String,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController, nextRoute = rootDestination)
        }
        composable(route = Screen.Login.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(viewModel = loginViewModel, navController = navController)
        }
        composable(route = Screen.Register.route) {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(viewModel = registerViewModel, navController = navController)
        }
        composable(route = "${Screen.Profile.route}/{${Screen.Profile.PATH_UID}}") {
            val id = it.arguments?.getString(Screen.Profile.PATH_UID)
            val profileViewModel: ProfileViewModel = hiltViewModel()
            if (id != null) {

                LaunchedEffect(Unit) {
                    profileViewModel.getProfile(id)
                    profileViewModel.getUserItems(id)
                }

                ProfileScreen(viewModel = profileViewModel, uid = id, navController = navController)
            }
        }
        composable(route = Screen.Main.route) {

            val homeViewModel: HomeViewModel = hiltViewModel()

            LaunchedEffect(key1 = homeViewModel) {
                homeViewModel.apply {
                    launch(Dispatchers.IO) {
                        getFlatmates()
                        getDashboard()
                        getItems()
                        getGeneralItems()
                    }
                }
            }

            HomeScreen(viewModel = homeViewModel, navController = navController)
        }
        composable(
            route = "${Screen.CreateItem.route}/{${Screen.CreateItem.PATH_ACTION_TYPE}}",
            arguments = listOf(
                navArgument(Screen.CreateItem.PATH_ACTION_TYPE) {
                    type = NavType.StringType
                }
            )
        ) {
            val createItemViewModel: CreateItemViewModel = hiltViewModel()
            val args = it.arguments?.getString(Screen.CreateItem.PATH_ACTION_TYPE)

            CreateGeneralItemScreen(
                viewModel = createItemViewModel,
                navController = navController,
                type = args
            )
        }

        composable(
            route = Screen.Setting.route
        ) {
            SettingScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}