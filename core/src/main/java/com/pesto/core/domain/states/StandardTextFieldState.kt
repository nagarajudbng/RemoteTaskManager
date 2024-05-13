package com.single.core.states

import com.pesto.core.presentation.Error

// Created by Nagaraju Deshetty on 08/05/24.


data class StandardTextFieldState(
    var text:String = "",
    val error: Error? = null

)