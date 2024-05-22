package com.pesto.profile.data.repository

import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.pesto.core.data.source.local.dao.ProfileDao
import com.pesto.core.domain.model.ProfileDomain
import com.pesto.profile.domain.model.LogoutResponse
import com.pesto.profile.domain.model.Response
import com.pesto.profile.domain.repository.AddImageToStorageResponse
import com.pesto.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Named


// Created by Nagaraju on 22/05/24.

class ProfileRepositoryImpl(
    @Named("Auth") private val databaseReference: DatabaseReference,
    private val profileDao: ProfileDao,
    private val sharedPreferences: SharedPreferences

    ):ProfileRepository {


    override suspend fun addImageToFirebaseStorage(imageUri: Uri, userName: String):AddImageToStorageResponse {
        return try {
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference
            val downloadUrl = storage.reference.child(userName).child("PROFILE_IMAGE_NAME3.png")
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            Log.d("downloadUrl", downloadUrl.toString())
            Response.Success(downloadUrl)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
    override suspend fun save(id: Long, userName: String, email: String, uri: Uri): AddImageToStorageResponse {
        val response=  addImageToFirebaseStorage(uri,userName)
        when(response) {
            is Response.Success ->{
                 sharedPreferences.edit().putString("image",response.data.toString()).apply()
            }
            is Response.Failure -> {
            }
            Response.Loading -> TODO()
        }
        return response
    }

    override suspend fun logout(): LogoutResponse {
        sharedPreferences.edit().clear().apply()
        return LogoutResponse.Success
    }

    override suspend fun getProfile(): ProfileDomain {
        return ProfileDomain(
            id = 0,
            name = sharedPreferences.getString("userName","")!!,
            image = sharedPreferences.getString("image","")!!,
            email = sharedPreferences.getString("email","")!!
        )
    }
}