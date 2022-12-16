package com.tafh.githubuserapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserEntity(
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    var id: Int = 0,

    @field:ColumnInfo(name = "username")
    var username: String? = null,

    @field:ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null,

    @field:ColumnInfo(name = "html_url")
    var htmlUrl: String? = null,

    @field:ColumnInfo(name = "favorited")
    var isFavorited: Boolean? = null

)