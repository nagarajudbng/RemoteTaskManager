package com.pesto.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileDomain(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val image: String,
    val email: String
)
