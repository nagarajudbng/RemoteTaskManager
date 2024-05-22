package com.pesto.profile.presentation

import android.net.Uri

sealed class ProfileEvent {
    data class EnteredUserName(val userName: String) : ProfileEvent()
    data class EnteredEmail(val email: String) : ProfileEvent()
    data class EnteredImageURI(val uri: Uri) : ProfileEvent()
    object Save:ProfileEvent()
}
