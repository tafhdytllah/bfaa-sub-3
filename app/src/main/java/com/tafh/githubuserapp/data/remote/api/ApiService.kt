package com.tafh.githubuserapp.data.remote.api

import com.tafh.githubuserapp.data.remote.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: Bearer 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")
    fun getSearchUser(
        @Query("q") q: String
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: Bearer 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<UserResponse>

    @GET("users/{username}/repos")
    @Headers("Authorization: Bearer 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")
    fun getUserRepositories(
        @Path("username") username: String
    ): Call<UserRepositoriesResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: Bearer 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<UserFollowerResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: Bearer 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<UserFollowingResponse>

}