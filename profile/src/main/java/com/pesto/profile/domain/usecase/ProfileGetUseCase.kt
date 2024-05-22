package com.pesto.profile.domain.usecase

import android.net.Uri
import android.util.Log
import com.pesto.core.domain.model.ProfileDomain
import com.pesto.profile.domain.repository.ProfileRepository
import javax.inject.Inject


// Created by Nagaraju on 22/05/24.

class ProfileGetUseCase @Inject constructor(
    private var repository: ProfileRepository
){

    suspend operator fun invoke():ProfileDomain{
        var value = repository.getProfile()
        Log.d("Profile test 2",value.toString())
            return value
    }
}