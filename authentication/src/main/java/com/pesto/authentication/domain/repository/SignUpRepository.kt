package com.pesto.authentication.domain.repository

import com.pesto.authentication.domain.model.SignInDomain
import com.pesto.authentication.domain.model.SignUpDomain


// Created by Nagaraju on 21/05/24.

interface SignUpRepository {
    suspend fun signUp(userName:String,email:String,password:String):SignUpDomain
    suspend fun signIn(userName: String,password: String): SignInDomain
}