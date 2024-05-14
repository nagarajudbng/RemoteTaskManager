package com.pesto.core.data.dto

import com.google.firebase.database.IgnoreExtraProperties


// Created by Nagaraju on 14/05/24.

@IgnoreExtraProperties
data class TaskDB(
    val id:String?=null,
    val title: String? = null,
    val description: String? = null,
    val status: String? = null,
    val dueDate: String? = null

)