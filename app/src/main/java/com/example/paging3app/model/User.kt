package com.example.paging3app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val age: Int,
    val birthDate: String,
    val bloodGroup: String,
    val email: String,
    val eyeColor: String,
    val firstName: String,
    val gender: String,
    val height: Double,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val lastName: String,
    val maidenName: String,
    val password: String,
    val phone: String,
    val weight: Double
)