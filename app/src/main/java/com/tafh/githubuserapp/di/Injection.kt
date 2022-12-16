package com.tafh.githubuserapp.di

import android.content.Context
import com.tafh.githubuserapp.data.UserRepository
import com.tafh.githubuserapp.data.local.room.UserDatabase
import com.tafh.githubuserapp.data.remote.retrofit.ApiConfig
import com.tafh.githubuserapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        val appExecutors = AppExecutors()
        return UserRepository.getInstance(apiService, dao, appExecutors)
    }
}