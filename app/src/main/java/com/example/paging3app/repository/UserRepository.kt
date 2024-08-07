package com.example.paging3app.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.paging3app.paging.UserPagingSource
import com.example.paging3app.remote.UserAPI
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI) {

    fun getUsers() = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 100),
        pagingSourceFactory = {UserPagingSource(userAPI)}
    ).liveData
}