package com.pesto.authentication.domain.model

import com.pesto.core.presentation.Validations


// Created by Nagaraju on 21/05/24.

data class SignUpResult(
    var validations: Validations,
    var isValid:Boolean = false
)