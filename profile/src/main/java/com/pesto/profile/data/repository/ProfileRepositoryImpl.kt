package com.pesto.profile.data.repository

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.pesto.core.data.mapper.toProfileDomain
import com.pesto.core.data.source.local.dao.ProfileDao
import com.pesto.core.data.source.local.entity.ProfileEntity
import com.pesto.core.domain.model.ProfileDomain
import com.pesto.profile.domain.repository.ProfileRepository
import javax.inject.Named


// Created by Nagaraju on 22/05/24.

class ProfileRepositoryImpl(
    @Named("Auth") private val databaseReference: DatabaseReference,
    private val profileDao: ProfileDao,

    ):ProfileRepository {
    override suspend fun save(id: Long, userName: String, email: String, uri: Uri) {
        val profile = ProfileEntity(
            id = id,
            userName = userName,
            emailID  = email,
            image = uri.toString()
        )
        profileDao.insert(profile)
      /*  val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val imageName = "${userName}.jpg"
        val imageRef = storageRef.child("images/$imageName")

        val uploadTask =imageRef.putFile(uri)

        val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation imageRef.downloadUrl
        })?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("ProfileRepository","Success")
                val downloadUri = task.result
//                addUploadRecordToDb(downloadUri.toString())
            } else {
                // Handle failures
            }
        }?.addOnFailureListener{

        }

       */
    }

    override suspend fun getProfile(): ProfileDomain {
        return profileDao.getProfile().toProfileDomain()
    }
}