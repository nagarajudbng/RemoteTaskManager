package com.pesto.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ProfileDomain(
    var id: Long = 0,
    var name: String = "",
    var image: String = "",
    var email: String =""
)
