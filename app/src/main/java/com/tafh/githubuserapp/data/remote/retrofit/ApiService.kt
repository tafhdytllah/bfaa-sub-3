package com.tafh.githubuserapp.data.remote.retrofit

import com.tafh.githubuserapp.data.remote.response.*
import com.tafh.githubuserapp.utils.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: $API_KEY")
    fun getSearchUser(
        @Query("q") q: String
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: $API_KEY")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<UserResponse>

    @GET("users/{username}/repos")
    @Headers("Authorization: $API_KEY")
    fun getUserRepositories(
        @Path("username") username: String
    ): Call<UserRepositoriesResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: $API_KEY")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<UserFollowerResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: $API_KEY")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<UserFollowingResponse>

}