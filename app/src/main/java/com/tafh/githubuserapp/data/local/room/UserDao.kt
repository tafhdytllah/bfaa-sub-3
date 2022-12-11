package com.tafh.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tafh.githubuserapp.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user where favorited = 1")
    fun getFavoritedUser(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(news: List<UserEntity>)

    @Update
    fun updateUser(news: UserEntity)

    @Query("DELETE FROM user WHERE favorited = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username AND favorited = 1)")
    fun isUserFavorited(username: String): Boolean

}