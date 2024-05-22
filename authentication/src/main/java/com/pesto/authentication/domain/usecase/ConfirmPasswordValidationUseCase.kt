package com.pesto.authentication.domain.usecase

import com.pesto.core.util.Constants
import com.pesto.core.presentation.Validations
import javax.inject.Inject


// Created by Nagaraju on 21/05/24.

class ConfirmPasswordValidationUseCase @Inject constructor(){
    suspend operator fun invoke(password:String, confirmPassword:String): Validations {
        return if (confirmPassword.length < Constants.MIN_PASSWORD_LENGTH) {
            Validations.PASSWORD_TOO_SHORT
        } else if (confirmPassword.length > Constants.MAX_PASSWORD_LENGTH) {
            Validations.USERNAME_TOO_LONG
        } else if (password != confirmPassword) {
            Validations.PASSWORD_NOT_MATCH
        } else  {
            Validations.PASSWORD_VALID
        }
    }
}