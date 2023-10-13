package com.linhtetko.monthly.ui.screens

sealed class Screen(val route: String) {

    companion object {
        private const val ROUTE_SPLASH = "splash"
        private const val ROUTE_REGISTER = "register"
        private const val ROUTE_LOGIN = "login"
        private const val ROUTE_PROFILE = "profile"
        private const val ROUTE_MAIN = "main"
        private const val ROUTE_CREATE_ITEM = "create-item"
        private const val ROUTE_SETTING = "setting"
    }

    data object Splash : Screen(route = ROUTE_SPLASH)

    data object Register : Screen(route = ROUTE_REGISTER)

    data object Login : Screen(route = ROUTE_LOGIN)

    data object Profile : Screen(route = ROUTE_PROFILE) {
        const val PATH_UID = "uid"
        fun route(path: String) = "$route/$path"
    }

    data object Main : Screen(route = ROUTE_MAIN)

    data object CreateItem : Screen(route = ROUTE_CREATE_ITEM) {

        const val PATH_ACTION_TYPE = "action_type"

        const val PATH_GENERAL = "general"
        const val PATH_ITEM = "items"

        fun route(path: String) = "$route/$path"
    }

    data object Setting: Screen(route = ROUTE_SETTING)
}
