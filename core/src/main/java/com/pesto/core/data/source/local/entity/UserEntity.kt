package com.pesto.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserEntity")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Long,
    val userName:String?=null,
    val emailID: String? = null,
    val image: String? = null
)
