package com.tafh.githubuserapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tafh.githubuserapp.data.remote.api.RetrofitConfig
import com.tafh.githubuserapp.data.remote.response.SearchUserResponse
import com.tafh.githubuserapp.data.remote.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel(){

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    companion object{
        private const val TAG = "HomeViewModel"
    }

    fun querySearchUser(queryString: String) {
        _isLoading.value = true
        _isEmpty.value = false
        val apiService = RetrofitConfig.getApiService()
        val client = apiService.getSearchUser(queryString)

        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
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

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                _isEmpty.value = true
            }
        })
    }

}