package com.pesto.authentication.domain.usecase

import com.pesto.core.util.Constants
import com.pesto.core.presentation.Validations
import javax.inject.Inject


// Created by Nagaraju on 21/05/24.

class PasswordValidationUseCase @Inject constructor() {
    suspend operator fun invoke(password:String): Validations {
        return if (password.length < Constants.MIN_PASSWORD_LENGTH) {
            Validations.PASSWORD_TOO_SHORT
        } else if (password.length > Constants.MAX_PASSWORD_LENGTH) {
            Validations.PASSWORD_TOO_LONG
        } else {
            Validations.PASSWORD_VALID
        }

    }
}