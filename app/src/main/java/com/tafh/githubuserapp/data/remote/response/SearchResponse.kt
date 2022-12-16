package com.tafh.githubuserapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<SearchItem>
)

data class SearchItem(
    @field:SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("html_url")
    val htmlUrl: String
)