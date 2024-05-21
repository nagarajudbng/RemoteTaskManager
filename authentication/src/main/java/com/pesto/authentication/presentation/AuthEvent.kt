package com.pesto.authentication.presentation

sealed class AuthEvent {
    data class EnteredUserName(val userName: String) : AuthEvent()
    data class EnteredEmail(val email: String) : AuthEvent()
    data class EnteredPassword(val password: String) : AuthEvent()
    data class EnteredConfirmPassword(val confirmPassword: String) : AuthEvent()

    object SignUP:AuthEvent()
    object SignIn:AuthEvent()
}
