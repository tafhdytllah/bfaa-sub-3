package com.tafh.githubuserapp.ui.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tafh.githubuserapp.data.remote.response.SearchItem
import com.tafh.githubuserapp.data.remote.response.UserFollowingResponse
import com.tafh.githubuserapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    private val _userFollowing = MutableLiveData<List<SearchItem>>()
    val userFollowing: LiveData<List<SearchItem>> = _userFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    fun getUserFollowing(username: String) {
        _isLoading.value = true
        _isEmpty.value = false
        val apiService = ApiConfig.getApiService()
        val apiClient = apiService.getUserFollowing(username)

        apiClient.enqueue(object : Callback<UserFollowingResponse> {
            override fun onResponse(
                call: Call<UserFollowingResponse>,
                response: Response<UserFollowingResponse>
            ) {
                _isLoading.value = false
                if (!response.isSuccessful) {
                    _isEmpty.value = true
                } else {
                    val following = response.body()!!
                    if (following.size > 0) {
                        _userFollowing.value = following
                    } else {
                        _isEmpty.value = true
                    }
                }
            }

            override fun onFailure(call: Call<UserFollowingResponse>, t: Throwable) {
                _isLoading.value = false
                _isEmpty.value = true
            }

        })
    }
}