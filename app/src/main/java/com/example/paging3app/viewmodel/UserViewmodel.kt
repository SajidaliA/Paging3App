package com.example.paging3app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.paging3app.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewmodel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userList = userRepository.getUsers().cachedIn(viewModelScope)
}