package com.pesto.authentication.data.mapper

import com.pesto.authentication.data.model.SignIn
import com.pesto.authentication.data.model.SignUp
import com.pesto.authentication.domain.model.SignInDomain
import com.pesto.authentication.domain.model.SignUpDomain


// Created by Nagaraju on 22/05/24.

fun SignUp.toSignUpDomain(): SignUpDomain {
    return SignUpDomain(
        isValid = isValid
    )
}