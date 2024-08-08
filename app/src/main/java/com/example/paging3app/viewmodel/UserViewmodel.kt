package com.example.paging3app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.paging3app.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewmodel @Inject constructor(userRepository: UserRepository) : ViewModel() {
    @ExperimentalPagingApi
    val userList = userRepository.getUsers().cachedIn(viewModelScope)
}