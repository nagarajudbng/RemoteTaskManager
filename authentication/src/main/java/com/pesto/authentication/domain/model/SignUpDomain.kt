package com.pesto.authentication.domain.model

import com.pesto.core.presentation.Validations


// Created by Nagaraju on 21/05/24.

data class SignUpDomain(
    var isAlreadyExists:Boolean = false,
    var isValid:Boolean = false
)