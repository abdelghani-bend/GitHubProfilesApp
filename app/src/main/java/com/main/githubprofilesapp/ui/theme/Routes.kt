package com.main.githubprofilesapp.ui.theme

sealed class Screen(val route: String) {
    object UserList : Screen("user_list")
    object UserDetails : Screen("user_details/{username}") {
        fun createRoute(username: String) = "user_details/$username"
    }
}