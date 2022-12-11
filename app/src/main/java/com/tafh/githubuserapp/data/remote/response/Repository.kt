package com.tafh.githubuserapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("name")
    val name: String?,
    @SerializedName("visibility")
    val visibility: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("updated_at")
    val updateAt: String?
)