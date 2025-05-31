package com.main.githubprofilesapp.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras

import com.main.githubprofilesapp.data.remote.GitHubService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserDetailsViewModelFactory(private val username: String?) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val apiService = retrofit.create(GitHubService::class.java)
            val repository = GitHubRepository(apiService)

            val savedStateHandle = extras.createSavedStateHandle()

            savedStateHandle["username"] = username

            @Suppress("UNCHECKED_CAST")
            return UserDetailsViewModel(repository, savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}