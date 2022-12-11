package com.tafh.githubuserapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @SerializedName("items")
    val items: List<User>,
    @SerializedName("total_count")
    val totalCount: Int
)