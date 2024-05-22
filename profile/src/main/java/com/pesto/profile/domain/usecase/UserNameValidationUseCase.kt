package com.pesto.profile.domain.usecase

import com.pesto.core.presentation.Validations
import com.pesto.core.util.Constants
import javax.inject.Inject


// Created by Nagaraju on 21/05/24.

class UserNameValidationUseCase @Inject constructor() {
    suspend operator fun invoke(userName:String): Validations {
        return if (userName.length > Constants.MAX_USERNAME_LENGTH) {
            Validations.USERNAME_TOO_LONG
        } else if (userName.length < Constants.MIN_USERNAME_LENGTH){
            Validations.USERNAME_TOO_SHORT
        } else {
            Validations.USERNAME_VALID
        }
    }
}