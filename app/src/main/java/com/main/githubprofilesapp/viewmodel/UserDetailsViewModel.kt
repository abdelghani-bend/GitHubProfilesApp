package com.main.githubprofilesapp.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.main.githubprofilesapp.data.model.GitHubUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UserDetailsUiState {
    object Loading : UserDetailsUiState()
    data class Success(val user: GitHubUser) : UserDetailsUiState()
    data class Error(val message: String) : UserDetailsUiState()
    object Initial : UserDetailsUiState()
}

class UserDetailsViewModel(
    private val repository: GitHubRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailsUiState>(UserDetailsUiState.Initial)
    val uiState: StateFlow<UserDetailsUiState> = _uiState

    private val username: String? = savedStateHandle["username"]

    init {
        username?.let {
            fetchUserDetails(it)
        } ?: run {
            _uiState.value = UserDetailsUiState.Error("Username not provided for details.")
        }
    }

    private fun fetchUserDetails(username: String) {
        _uiState.value = UserDetailsUiState.Loading
        viewModelScope.launch {
            try {
                val userDetails = repository.getUserDetails(username)
                _uiState.value = UserDetailsUiState.Success(userDetails)
            } catch (e: Exception) {
                _uiState.value = UserDetailsUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}