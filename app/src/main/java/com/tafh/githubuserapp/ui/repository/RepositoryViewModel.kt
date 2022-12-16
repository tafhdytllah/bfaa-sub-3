package com.tafh.githubuserapp.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tafh.githubuserapp.data.remote.response.Repository
import com.tafh.githubuserapp.data.remote.response.UserRepositoriesResponse
import com.tafh.githubuserapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel: ViewModel() {

    private val _userRepo = MutableLiveData<List<Repository>>()
    val userRepo: LiveData<List<Repository>> = _userRepo

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    fun getUserRepo(username: String) {
        _isLoading.value = true
        _isEmpty.value = false
        val apiService = ApiConfig.getApiService()
        val apiClient = apiService.getUserRepositories(username)

        apiClient.enqueue(object : Callback<UserRepositoriesResponse> {
            override fun onResponse(
                call: Call<UserRepositoriesResponse>,
                response: Response<UserRepositoriesResponse>
            ) {
                _isLoading.value = false
                if (!response.isSuccessful) {
                    _isEmpty.value = true
                } else {
                    val repo = response.body()!!
                    if (repo.size > 0) {
                        _userRepo.value = repo
                    } else {
                        _isEmpty.value = true
                    }
                }
            }

            override fun onFailure(call: Call<UserRepositoriesResponse>, t: Throwable) {
                _isLoading.value = false
                _isEmpty.value = true
            }

        })
    }

}