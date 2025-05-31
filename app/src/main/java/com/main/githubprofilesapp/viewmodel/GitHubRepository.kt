package com.main.githubprofilesapp.viewmodel

import com.main.githubprofilesapp.data.model.GitHubUser
import com.main.githubprofilesapp.data.remote.GitHubService

class GitHubRepository(private val apiService: GitHubService) {

    suspend fun getUsers(since: Int, perPage: Int): List<GitHubUser> {
        return apiService.getUsers(since, perPage)
    }

    suspend fun getUserDetails(username: String): GitHubUser {
        return apiService.getUserDetails(username)
    }
}