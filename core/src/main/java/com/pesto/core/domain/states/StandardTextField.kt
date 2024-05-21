package com.single.core.states

import com.pesto.core.presentation.Error
import com.pesto.core.presentation.Validations

// Created by Nagaraju Deshetty on 08/05/24.


data class StandardTextField(
    var text:String = "",
    val statusText:String? = null
)