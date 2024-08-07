package com.example.paging3app.remote

import com.example.paging3app.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserAPI {

    @GET("/users")
    suspend fun getUsers(@Query("limit") limit: Int = 10, @Query("skip") skip: Int) : UserResponse

}