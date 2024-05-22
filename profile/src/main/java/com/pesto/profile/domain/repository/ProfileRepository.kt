package com.pesto.profile.domain.repository

import android.net.Uri
import com.pesto.core.domain.model.ProfileDomain

interface ProfileRepository {
    suspend fun save(id:Long,userName:String, email:String,uri: Uri)
    suspend fun getProfile(): ProfileDomain
}
