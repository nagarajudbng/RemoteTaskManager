package com.pesto.profile.domain.repository

import android.net.Uri
import com.pesto.core.domain.model.ProfileDomain
import com.pesto.profile.domain.model.LogoutResponse
import com.pesto.profile.domain.model.Response

typealias AddImageToStorageResponse = Response<Uri>
typealias GetImageUrlFromFirestoreResponse = Response<String>
interface ProfileRepository {
    suspend fun save(id:Long,userName:String, email:String,uri: Uri): AddImageToStorageResponse
    suspend fun logout(): LogoutResponse
    suspend fun getProfile(): ProfileDomain

     suspend fun addImageToFirebaseStorage(imageUri: Uri, userName: String):AddImageToStorageResponse
//     suspend fun getImageUrlFromFirestore(): GetImageUrlFromFirestoreResponse {

}
