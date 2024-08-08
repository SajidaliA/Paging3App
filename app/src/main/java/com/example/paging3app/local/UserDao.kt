package com.example.paging3app.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paging3app.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getUsers(): PagingSource<Int, User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: List<User>)

    @Query("DELETE FROM User")
    suspend fun deleteUsers()
}