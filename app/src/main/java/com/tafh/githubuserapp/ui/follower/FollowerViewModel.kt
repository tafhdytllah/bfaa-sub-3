package com.tafh.githubuserapp.ui.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tafh.githubuserapp.data.remote.response.SearchItem
import com.tafh.githubuserapp.data.remote.response.UserFollowerResponse
import com.tafh.githubuserapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {
    private val _userFollower = MutableLiveData<List<SearchItem>>()
    val userFollower: LiveData<List<SearchItem>> = _userFollower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    fun getUserFollower(username: String) {
        _isLoading.value = true
        _isEmpty.value = false
        val apiService = ApiConfig.getApiService()
        val apiClient = apiService.getUserFollowers(username)

        apiClient.enqueue(object : Callback<UserFollowerResponse> {
            override fun onResponse(
                call: Call<UserFollowerResponse>,
                response: Response<UserFollowerResponse>
            ) {
                _isLoading.value = false
                if (!response.isSuccessful) {
                    _isEmpty.value = true
                } else {
                    val follower = response.body()!!
                    if (follower.size > 0) {
                        _userFollower.value = follower
                    } else {
                        _isEmpty.value = true
                    }
                }
            }

            override fun onFailure(call: Call<UserFollowerResponse>, t: Throwable) {
                _isLoading.value = false
                _isEmpty.value = true
            }
        })
    }
}