package com.pesto.authentication.domain.usecase

import com.pesto.authentication.domain.model.SignInDomain
import com.pesto.authentication.domain.repository.SignUpRepository
import javax.inject.Inject


// Created by Nagaraju on 21/05/24.

class SignInUseCase @Inject constructor(
    private var repository: SignUpRepository
) {
    suspend operator fun invoke(userName:String,password:String): SignInDomain {
        return repository.signIn(userName, password)
    }
}