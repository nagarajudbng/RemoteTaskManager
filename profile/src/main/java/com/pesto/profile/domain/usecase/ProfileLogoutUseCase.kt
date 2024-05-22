package com.pesto.profile.domain.usecase

import com.pesto.profile.domain.model.LogoutResponse
import com.pesto.profile.domain.repository.ProfileRepository
import javax.inject.Inject


// Created by Nagaraju on 22/05/24.

class ProfileLogoutUseCase @Inject constructor(
    var repository: ProfileRepository
){

    suspend operator fun invoke(): LogoutResponse {
        return repository.logout()
    }
}