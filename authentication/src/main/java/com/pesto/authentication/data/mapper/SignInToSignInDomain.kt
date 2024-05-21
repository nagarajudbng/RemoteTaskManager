package com.pesto.authentication.data.mapper

import com.pesto.authentication.data.model.SignIn
import com.pesto.authentication.domain.model.SignInDomain


// Created by Nagaraju on 22/05/24.

fun SignIn.toSignInDomain():SignInDomain{
    return SignInDomain(
        isValid = isValid
    )
}