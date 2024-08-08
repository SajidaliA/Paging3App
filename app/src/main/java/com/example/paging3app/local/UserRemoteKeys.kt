package com.example.paging3app.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val previousKey: Int?,
    val nextKey: Int?
)
