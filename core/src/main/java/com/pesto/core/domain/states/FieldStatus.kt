package com.pesto.core.domain.states

import com.pesto.core.presentation.Error
// Created by Nagaraju Deshetty on 07/05/24.


sealed class FieldStatus : Error(){
    data object FieldEmpty: FieldStatus()
    data object FieldFilled: FieldStatus()
    data object InputTooShort : FieldStatus()
}