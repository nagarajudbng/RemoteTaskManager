package com.pesto.authentication.data.model

import com.google.firebase.database.IgnoreExtraProperties


// Created by Nagaraju on 21/05/24.

@IgnoreExtraProperties
data class UserDB(
    val userName:String?=null,
    val emailID: String? = null,
    val password: String? = null,
    val image:String? = null
)