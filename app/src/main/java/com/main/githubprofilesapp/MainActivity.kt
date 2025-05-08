package com.main.githubprofilesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.main.githubprofilesapp.ui.GitHubUsersScreen
import com.main.githubprofilesapp.ui.theme.GitHubProfilesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubProfilesAppTheme {
                GitHubUsersScreen()
            }
        }
    }
}
