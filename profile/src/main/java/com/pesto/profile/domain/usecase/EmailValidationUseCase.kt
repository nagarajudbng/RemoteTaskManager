package com.pesto.profile.domain.usecase

import com.pesto.core.presentation.Validations
import javax.inject.Inject


// Created by Nagaraju on 21/05/24.

class EmailValidationUseCase @Inject constructor() {

    suspend operator fun invoke(email:String): Validations {
        val atIndex = email.indexOf('@')
        val dotIndex = email.lastIndexOf('.')

        return if((atIndex > 0 &&
                    dotIndex > atIndex + 1 &&
                    dotIndex < email.length - 2)){
            Validations.EMAIL_VALID

        }
        else {
            Validations.EMAIL_NOT_VALID
        }

    }
}