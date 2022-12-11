package com.tafh.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tafh.githubuserapp.data.remote.api.RetrofitConfig
import com.tafh.githubuserapp.data.remote.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {

    private val _userDetail = MutableLiveData<UserResponse>()
    val userDetail: LiveData<UserResponse> = _userDetail

    private val _userFollower = MutableLiveData<List<User>>()
    val userFollower: LiveData<List<User>> = _userFollower

    private val _userRepo = MutableLiveData<List<Repository>>()
    val userRepo: LiveData<List<Repository>> = _userRepo

    private val _userFollowing = MutableLiveData<List<User>>()
    val userFollowing: LiveData<List<User>> = _userFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val apiService = RetrofitConfig.getApiService()

    companion object {
        private const val TAG = "DetailUserViewModel"
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true
        _isEmpty.value = false
        val apiService = RetrofitConfig.getApiService()
        val client = apiService.getDetailUser(username)

        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (!response.isSuccessful) {
                    _isEmpty.value = true
                } else {
                    val data = response.body()!!
                    _userDetail.value = data
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                _isEmpty.value = true
            }

        })

    }

    fun getUserFollowing(username: String) {
        _isLoading.value = true
        _isEmpty.value = false
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

    fun getUserRepo(username: String) {
        _isLoading.value = true
        _isEmpty.value = false
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

    fun getUserFollower(username: String) {
        _isLoading.value = true
        _isEmpty.value = false
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