package com.example.paging3app.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM UserRemoteKeys WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): UserRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKeys: List<UserRemoteKeys>)

    @Query("DELETE FROM UserRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}