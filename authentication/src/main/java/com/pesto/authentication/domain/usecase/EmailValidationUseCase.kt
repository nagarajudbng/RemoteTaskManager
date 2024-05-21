package com.pesto.authentication.domain.usecase

import com.pesto.authentication.domain.model.SignUpResult
import com.pesto.core.presentation.Validations
import javax.inject.Inject


// Created by Nagaraju on 21/05/24.

class EmailValidationUseCase @Inject constructor() {

    suspend operator fun invoke(email:String): SignUpResult {
        val atIndex = email.indexOf('@')
        val dotIndex = email.lastIndexOf('.')

        return if((atIndex > 0 &&
                    dotIndex > atIndex + 1 &&
                    dotIndex < email.length - 2)){
            SignUpResult(
                validations = Validations.EMAIL_VALID,
                isValid = true)
        }
        else {
            SignUpResult(
                validations = Validations.EMAIL_NOT_VALID,
                isValid = false
            )
        }

    }
}