package com.tafh.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tafh.githubuserapp.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Update
    suspend fun updateUser(user: UserEntity)

    // delete all favorite user
    @Query("DELETE FROM user")
    fun deleteAll()

    // insert data user
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: UserEntity)

    // get all user favorited
    @Query("SELECT * FROM user WHERE favorited = 1 ORDER BY id ASC")
    fun getFavoritedUsers(): LiveData<List<UserEntity>>

    // get user by username
    @Query("SELECT * FROM user WHERE username = :username")
    fun getUserByUsername(username: String): LiveData<UserEntity>


//
//    // delete by username
//    @Query("DELETE FROM user WHERE username = :username")
//    fun deleteByUsername(username: String)



    // get user is favorited?
//    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username AND favorited = 1)")
//    fun isUserFavorited(username: String): Boolean

//    @Update
//    fun updateUser(user: UserEntity)
//






}