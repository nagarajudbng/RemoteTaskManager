package com.pesto.profile.domain.usecase

import android.net.Uri
import com.pesto.profile.domain.repository.AddImageToStorageResponse
import com.pesto.profile.domain.repository.ProfileRepository
import javax.inject.Inject


// Created by Nagaraju on 22/05/24.

class ProfileSaveUseCase @Inject constructor(
    var repository: ProfileRepository
){

    suspend operator fun invoke(id:Long,userName:String, email:String,uri: Uri): AddImageToStorageResponse {
        return repository.save(id,userName,email,uri)
    }
}