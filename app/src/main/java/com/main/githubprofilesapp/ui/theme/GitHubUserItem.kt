package com.main.githubprofilesapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.main.githubprofilesapp.data.model.GitHubUser

@Composable
fun GitHubUserItem(user: GitHubUser) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Image(
            painter = rememberAsyncImagePainter(user.avatar_url),
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = user.login, style = MaterialTheme.typography.bodyLarge)
    }
}
