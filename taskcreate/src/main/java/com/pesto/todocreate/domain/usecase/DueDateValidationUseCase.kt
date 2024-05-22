package com.pesto.todocreate.domain.usecase

import com.pesto.todocreate.domain.util.InputStatus
import javax.inject.Inject


// Created by Nagaraju on 15/05/24.
class DueDateValidationUseCase @Inject constructor(){

    operator fun invoke(title: String): InputStatus {

        return if (title.isEmpty() || title=="Due date")
            InputStatus.EMPTY
        else
            InputStatus.VALID

    }
}