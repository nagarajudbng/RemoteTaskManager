package com.pesto.core.domain.model

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Task(
    var id:Long = 0,
    var title: String,
    var description: String,
    var status: String
)
