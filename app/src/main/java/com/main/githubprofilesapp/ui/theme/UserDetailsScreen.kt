package com.main.githubprofilesapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.main.githubprofilesapp.data.model.GitHubUser
import com.main.githubprofilesapp.viewmodel.UserDetailsUiState
import com.main.githubprofilesapp.viewmodel.UserDetailsViewModel
import com.main.githubprofilesapp.viewmodel.UserDetailsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    username: String?, // Still passed from NavHost
    viewModel: UserDetailsViewModel = viewModel(
        factory = UserDetailsViewModelFactory (username)
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = username ?: "User Details") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            when (uiState) {
                UserDetailsUiState.Loading -> {
                    Spacer(modifier = Modifier.height(64.dp))
                    CircularProgressIndicator()
                }
                is UserDetailsUiState.Success -> {
                    val user = (uiState as UserDetailsUiState.Success).user
                    UserDetailsContent(user = user)
                }
                is UserDetailsUiState.Error -> {
                    Spacer(modifier = Modifier.height(64.dp))
                    Text(
                        text = "Error: ${(uiState as UserDetailsUiState.Error).message}",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                UserDetailsUiState.Initial -> {
                    if (username == null) {
                        Text(text = "No username provided.", color = MaterialTheme.colorScheme.error)
                    } else {
                        Text(text = "Loading details for $username...")
                    }
                }
            }
        }
    }
}

@Composable
fun UserDetailsContent(user: GitHubUser) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // User Avatar
        Image(
            painter = rememberAsyncImagePainter(user.avatar_url),
            contentDescription = "User Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Name and Login
        user.name?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = "@${user.login}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Bio
        user.bio?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Stats (Followers, Following, Public Repos)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            user.followers?.let { StatItem(count = it, label = "Followers") }
            user.following?.let { StatItem(count = it, label = "Following") }
            user.public_repos?.let { StatItem(count = it, label = "Repos") }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Contact/Location Details (Example)
        user.company?.let { DetailItem(icon = Icons.Default.Build, text = it) }
        user.location?.let { DetailItem(icon = Icons.Default.LocationOn, text = it) }
        user.email?.let { DetailItem(icon = Icons.Default.Email, text = it) }
        user.blog?.let { DetailItem(icon = Icons.Default.Info, text = it) }
        // You might want to make blog clickable
    }
}

@Composable
fun StatItem(count: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(text = label, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun DetailItem(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}