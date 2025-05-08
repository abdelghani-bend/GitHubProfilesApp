package com.main.githubprofilesapp.data.model

data class GitHubUser(
    val id: Int,
    val login: String,
    val avatar_url: String
)

data class SearchResponse(
    val items: List<GitHubUser>
)
