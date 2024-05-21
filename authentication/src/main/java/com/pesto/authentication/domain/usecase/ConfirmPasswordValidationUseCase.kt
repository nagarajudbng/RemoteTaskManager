package com.pesto.authentication.domain.usecase

import com.pesto.authentication.domain.model.SignUpResult
import com.pesto.authentication.domain.util.Constants
import com.pesto.core.presentation.Validations
import javax.inject.Inject


// Created by Nagaraju on 21/05/24.

class ConfirmPasswordValidationUseCase @Inject constructor(){
    suspend operator fun invoke(password:String, confirmPassword:String): SignUpResult {
        var  result: Validations = Validations.PASSWORD_NOT_MATCH
        result = if (confirmPassword.length < Constants.MIN_PASSWORD_LENGTH) {
            Validations.PASSWORD_TOO_SHORT
        } else if (confirmPassword.length > Constants.MAX_PASSWORD_LENGTH) {
            Validations.USERNAME_TOO_LONG
        } else if (password != confirmPassword) {
            Validations.PASSWORD_NOT_MATCH
        } else  {
            Validations.PASSWORD_VALID
        }
        return SignUpResult(validations = result)
    }
}