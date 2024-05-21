package com.pesto.authentication.domain.usecase

import com.pesto.authentication.domain.model.SignUpResult
import com.pesto.authentication.domain.util.Constants
import com.pesto.core.presentation.Validations
import javax.inject.Inject


// Created by Nagaraju on 21/05/24.

class UserNameValidationUseCase @Inject constructor() {
    suspend operator fun invoke(userName:String): SignUpResult {
        var result = Validations.USERNAME_TOO_SHORT
        result = if (userName.length > Constants.MAX_USERNAME_LENGTH) {
            Validations.USERNAME_TOO_LONG
        } else if (userName.length < Constants.MIN_USERNAME_LENGTH){
            Validations.USERNAME_TOO_SHORT
        } else {
            Validations.USERNAME_VALID
        }
        return SignUpResult(validations = result)
    }
}