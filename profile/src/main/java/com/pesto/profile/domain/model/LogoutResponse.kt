package com.pesto.profile.domain.model

data class LogoutResponse (
    val message: String
) {
    companion object {
        val Success: LogoutResponse
            get() = LogoutResponse("Logout Success")
        val Fail: LogoutResponse
            get() = LogoutResponse("Logout Fail")
    }
}
