package com.pesto.authentication.domain.usecase

import com.pesto.authentication.domain.model.SignUpDomain
import com.pesto.authentication.domain.repository.SignUpRepository
import javax.inject.Inject


// Created by Nagaraju on 21/05/24.

class SignUpUseCase @Inject constructor(
    private var repository: SignUpRepository
) {
    suspend operator fun invoke(userName:String,email:String,password:String): SignUpDomain {
        return repository.signUp(userName, email, password)
    }
}