package com.pesto.profile.domain.usecase

import android.net.Uri
import com.pesto.profile.domain.repository.ProfileRepository
import javax.inject.Inject


// Created by Nagaraju on 22/05/24.

class ProfileSaveUseCase @Inject constructor(
    var repository: ProfileRepository
){

    suspend operator fun invoke(id:Long,userName:String, email:String,uri: Uri){
        repository.save(id,userName,email,uri)
    }
}