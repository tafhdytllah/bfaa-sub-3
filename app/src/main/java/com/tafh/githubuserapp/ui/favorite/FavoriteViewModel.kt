package com.tafh.githubuserapp.ui.favorite

import androidx.lifecycle.ViewModel
import com.tafh.githubuserapp.data.UserRepository

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFavoritedUsers() = userRepository.getFavoritedUsers()

    fun deleteAll() = userRepository.deleteAll()

}