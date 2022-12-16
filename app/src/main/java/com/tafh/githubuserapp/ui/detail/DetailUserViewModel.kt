package com.tafh.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tafh.githubuserapp.data.UserRepository
import com.tafh.githubuserapp.data.local.entity.UserEntity
import com.tafh.githubuserapp.data.remote.retrofit.ApiConfig
import com.tafh.githubuserapp.data.remote.response.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userDetail = MutableLiveData<UserResponse>()
    val userDetail: LiveData<UserResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    fun getUserByUsername(username: String): LiveData<UserEntity> = userRepository.getUserByUsername(username)

    fun setFavorite(user: UserEntity) {
        viewModelScope.launch {
            userRepository.setFavorite(user, true)
        }
    }

    fun deleteFavorite(user: UserEntity) {
        viewModelScope.launch {
            userRepository.setFavorite(user, false)
        }
    }

    fun insertUser(user: UserEntity) = userRepository.insertUser(user)



//    fun deleteByUsername(username: String) {
//        userRepository.deleteByUserName(username)
//    }



    fun getDetailUser(username: String) {
        _isLoading.value = true
        _isEmpty.value = false
        val apiService = ApiConfig.getApiService()
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

}