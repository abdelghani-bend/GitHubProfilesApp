package com.main.githubprofilesapp.data.remote

import com.main.githubprofilesapp.data.model.GitHubUser
import com.main.githubprofilesapp.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("users")
    suspend fun getDefaultUsers(
        @Query("since") since: Int = 0,
        @Query("per_page") perPage: Int = 30
    ): List<GitHubUser>

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30
    ): SearchResponse
}

interface GitHubService {
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<GitHubUser>

    // --- ADD THIS ENDPOINT ---
    @GET("users/{username}")
    suspend fun getUserDetails(@Path("username") username: String): GitHubUser
}


