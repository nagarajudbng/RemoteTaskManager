package com.pesto.todocreate.domain.usecase

import com.pesto.todocreate.domain.util.InputStatus
import javax.inject.Inject


// Created by Nagaraju on 15/05/24.
class TitleValidationUseCase @Inject constructor(){

    operator fun invoke(title: String): InputStatus {
        if (title.contains("Error")) {
            throw IllegalArgumentException("Title cannot contain 'Error' text.")
        }
        return if (title.isEmpty())
            InputStatus.EMPTY
        else if(title.length<10)
            InputStatus.LENGTH_TOO_SHORT
        else
            InputStatus.VALID

    }
}