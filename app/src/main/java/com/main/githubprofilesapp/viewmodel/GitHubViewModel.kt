package com.main.githubprofilesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.main.githubprofilesapp.data.model.GitHubUser
import com.main.githubprofilesapp.data.remote.GitHubApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitHubViewModel : ViewModel() {
    private val _users = MutableStateFlow<List<GitHubUser>>(emptyList())
    val users: StateFlow<List<GitHubUser>> = _users

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private var currentPage = 1
    private var since = 0
    private var isLoading = false
    private var lastQuery = ""

    private val api = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitHubApi::class.java)

    init {
        loadUsers()
    }

    fun loadUsers(reset: Boolean = false) {
        if (isLoading) return
        isLoading = true

        val query = searchQuery.value.trim()

        viewModelScope.launch {
            try {
                if (reset) {
                    currentPage = 1
                    since = 0
                    _users.value = emptyList()
                }

                if (query.isNotEmpty()) {
                    val response = api.searchUsers(query, currentPage++)
                    _users.update { it + response.items }
                    lastQuery = query
                } else {
                    val defaultUsers = api.getDefaultUsers(since = since)
                    _users.update { it + defaultUsers }
                    since += 30
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        loadUsers(reset = true)
    }
}