package com.tafh.githubuserapp.ui.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tafh.githubuserapp.data.UserRepository
import com.tafh.githubuserapp.di.Injection

class DetailUserViewModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: DetailUserViewModelFactory? = null
        fun getInstance(context: Context): DetailUserViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: DetailUserViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}