package com.tafh.githubuserapp.data.remote.retrofit

import com.tafh.githubuserapp.data.remote.response.*
import com.tafh.githubuserapp.utils.Constants.auth
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: $auth")
    fun getSearchUser(
        @Query("q") q: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: $auth")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<UserResponse>

    @GET("users/{username}/repos")
    @Headers("Authorization: $auth")
    fun getUserRepositories(
        @Path("username") username: String
    ): Call<UserRepositoriesResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: $auth")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<UserFollowerResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: $auth")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<UserFollowingResponse>

}