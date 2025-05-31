package com.main.githubprofilesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.main.githubprofilesapp.ui.GitHubUsersScreen
import com.main.githubprofilesapp.ui.theme.GitHubProfilesAppTheme
import com.main.githubprofilesapp.ui.theme.Screen
import com.main.githubprofilesapp.ui.theme.UserDetailsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubProfilesAppTheme {
                MyAppNavHost()
            }
        }
    }
}

@Composable
fun MyAppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.UserList.route
    ) {
        composable(Screen.UserList.route) {
            GitHubUsersScreen(
                onUserClick = { username ->
                    navController.navigate(Screen.UserDetails.createRoute(username))
                }
            )
        }
        composable(
            route = Screen.UserDetails.route,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            UserDetailsScreen(username = username)
        }
    }
}
