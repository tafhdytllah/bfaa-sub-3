package com.tafh.githubuserapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.tafh.githubuserapp.data.local.entity.UserEntity
import com.tafh.githubuserapp.data.local.room.UserDao
import com.tafh.githubuserapp.data.remote.retrofit.ApiService
import com.tafh.githubuserapp.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Result

class UserResponse private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<Result<List<UserEntity>>>()

}