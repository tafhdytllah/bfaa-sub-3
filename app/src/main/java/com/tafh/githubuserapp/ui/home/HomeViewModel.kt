package com.tafh.githubuserapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tafh.githubuserapp.data.UserRepository
import com.tafh.githubuserapp.data.local.entity.UserEntity
import com.tafh.githubuserapp.data.remote.response.SearchItem
import com.tafh.githubuserapp.data.remote.response.SearchResponse
import com.tafh.githubuserapp.data.remote.retrofit.ApiConfig
import com.tafh.githubuserapp.data.remote.response.SearchUserResponse
import com.tafh.githubuserapp.data.remote.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val userRepository: UserRepository) : ViewModel(){

    private val _users = MutableLiveData<List<SearchItem>>()
    val users: LiveData<List<SearchItem>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

//    fun getSearchUser(query: String) = userRepository.getSearchUser(query)

//    fun getBookmarkedNews() = userRepository.getFavoritedUser()
//
//    fun saveUser(user: UserEntity) {
//        userRepository.setFavoritedUser(user, true)
//    }
//
//    fun deleteNews(user: UserEntity) {
//        userRepository.setFavoritedUser(user, false)
//    }

    fun querySearchUser(queryString: String) {
        _isLoading.value = true
        _isEmpty.value = false
        val apiService = ApiConfig.getApiService()
        val client = apiService.getSearchUser(queryString)

        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (!response.isSuccessful) {
                    _isEmpty.value = true
                } else {
                    val data = response.body()!!.items
                    if (data.size.equals(0)) {
                        _isEmpty.value = true
                    } else {
                        _users.value = data
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                _isEmpty.value = true
            }
        })
    }

}