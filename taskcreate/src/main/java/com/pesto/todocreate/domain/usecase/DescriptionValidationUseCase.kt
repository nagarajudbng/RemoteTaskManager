package com.pesto.todocreate.domain.usecase

import com.pesto.todocreate.domain.util.InputStatus
import javax.inject.Inject


// Created by Nagaraju on 15/05/24.
class DescriptionValidationUseCase @Inject constructor(){

    operator fun invoke(description: String): InputStatus {
       return if (description.isEmpty())
            InputStatus.EMPTY
       else
            InputStatus.VALID
    }
}