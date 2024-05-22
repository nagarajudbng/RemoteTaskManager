package com.pesto.core.data.mapper

import com.pesto.core.data.source.local.entity.ProfileEntity
import com.pesto.core.domain.model.ProfileDomain


// Created by Nagaraju on 22/05/24.
fun ProfileEntity.toProfileDomain(): ProfileDomain {
    return ProfileDomain(
        id = this.id,
        name = this.userName ?: "",
        image = this.image ?: "",
        email = this.emailID ?: ""
    )
}