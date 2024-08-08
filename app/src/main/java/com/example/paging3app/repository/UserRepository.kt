package com.example.paging3app.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.paging3app.local.UserDatabase
import com.example.paging3app.paging.UserRemoteMediator
import com.example.paging3app.remote.UserAPI
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userAPI: UserAPI,
    private val userDataBase: UserDatabase
) {


    @ExperimentalPagingApi
    fun getUsers() = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 100),
        remoteMediator = UserRemoteMediator(userAPI, userDataBase),
        pagingSourceFactory = { userDataBase.getUserDao().getUsers() }
    ).liveData
}