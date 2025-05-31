package com.main.githubprofilesapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.main.githubprofilesapp.viewmodel.GitHubViewModel

@Composable
fun GitHubUsersScreen(
    viewModel: GitHubViewModel = viewModel(),
    onUserClick: (String) -> Unit
) {
    val users by viewModel.users.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            label = { Text("Search GitHub users") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(users) { index, user ->
                GitHubUserItem(user = user, onUserClick = onUserClick)
                if (index < users.lastIndex) {
                    Divider()
                }
                if (index >= users.lastIndex - 5) {
                    viewModel.loadUsers()
                }
            }
        }
    }
}