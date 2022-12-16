package com.tafh.githubuserapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.tafh.githubuserapp.data.local.entity.UserEntity
import com.tafh.githubuserapp.data.local.room.UserDao
import com.tafh.githubuserapp.data.remote.response.SearchItem
import com.tafh.githubuserapp.data.remote.response.SearchResponse
import com.tafh.githubuserapp.data.remote.retrofit.ApiService
import com.tafh.githubuserapp.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val appExecutors: AppExecutors
){
    private val resultResponse = MediatorLiveData<Result<List<SearchItem>>>()

    suspend fun setFavorite(user: UserEntity, favoriteState: Boolean) {
        user.isFavorited = favoriteState
        userDao.updateUser(user)
    }

    fun getSearchUser(query: String): LiveData<Result<List<SearchItem>>> {
        resultResponse.value = Result.Loading

        val client = apiService.getSearchUser(q = query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    val users = response.body()?.items
                    if (users != null) {
                        resultResponse.value = Result.Success(users)
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                resultResponse.value = Result.Error(t.message.toString())
            }
        })

        return resultResponse
    }

    fun getFavoritedUsers(): LiveData<List<UserEntity>> {
        return userDao.getFavoritedUsers()
    }

    fun getUserByUsername(username: String): LiveData<UserEntity> = userDao.getUserByUsername(username)

    fun insertUser(user: UserEntity) {
        appExecutors.diskIO.execute {
            userDao.insertUser(user)
        }
    }


    fun deleteAll() {
        appExecutors.diskIO.execute { userDao.deleteAll() }
    }

//    fun deleteByUserName(username: String) = userDao.deleteByUsername(username)

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao,
            appExecutors: AppExecutors
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userDao, appExecutors)
            }.also { instance = it }
    }
}