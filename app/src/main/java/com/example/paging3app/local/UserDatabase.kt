package com.example.paging3app.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paging3app.model.User

@Database(entities = [User::class, UserRemoteKeys::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}