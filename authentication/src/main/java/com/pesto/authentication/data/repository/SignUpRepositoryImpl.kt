package com.pesto.authentication.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.pesto.authentication.data.model.UserDB
import com.pesto.authentication.domain.model.SignInDomain
import com.pesto.authentication.domain.model.SignUpDomain
import com.pesto.authentication.domain.repository.SignUpRepository
import com.pesto.core.data.source.local.dao.UserDao
import com.pesto.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Named
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


// Created by Nagaraju on 21/05/24.

class SignUpRepositoryImpl(
    @Named("Auth") private val databaseReference: DatabaseReference,
    private val userDao: UserDao,
    private val sharedPreferences: SharedPreferences
):SignUpRepository {

    override suspend fun signUp(userName: String, email: String, password: String):SignUpDomain {
        val user = UserDB(userName = userName, emailID = email, password = password)
        return  suspendCoroutine { continuation ->
            val checkUserDB = databaseReference.orderByChild("userName").equalTo(userName)
            checkUserDB.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Log.d("SignUpRepositoryImpl", "Data Already Exist")
                        continuation.resume(SignUpDomain(isValid = false, isAlreadyExists = true))
                    } else {
                        databaseReference.child(userName).setValue(user)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Value was successfully set
                                    Log.d("SignUpRepositoryImpl", "Data saved successfully")
                                    continuation.resume(SignUpDomain(isValid = true))
                                } else {
                                    // There was an error
                                    Log.d(
                                        "SignUpRepositoryImpl",
                                        "Failed to save data: ${task.exception}"
                                    )
                                    continuation.resume(SignUpDomain(isValid = false))
                                }
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("SignUpRepositoryImpl", "Failed to save data: ${error.message}")
                    continuation.resume(SignUpDomain(isValid = false))
                }

            })
        }



    }

    override suspend fun signIn(userName: String, password: String): SignInDomain {

        return suspendCoroutine { continuation ->
            val checkUserDB = databaseReference.orderByChild("userName").equalTo(userName)
            checkUserDB.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var passwordDB = snapshot.child(userName).child("password").getValue(String::class.java)
                        var email = snapshot.child(userName).child("emailID").getValue(String::class.java)
                        if(passwordDB.equals(password)){
                            val userEntity= UserEntity(
                                id = 0,
                                userName = userName,
                                emailID = email
                            )
                            CoroutineScope(Dispatchers.IO).launch {
//                                userDao.delete()
//                                userDao.insert(userEntity)
                                sharedPreferences.edit().putString("userName",userName).putString("email",email).apply()

                            }
                            continuation.resume(SignInDomain(isValid = true))
                        }else {
                            continuation.resume(SignInDomain(isValid = false))
                        }
                    }else {
                        continuation.resume(SignInDomain(isValid = false))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resume(SignInDomain(isValid = false))
                }

            })

        }
    }
}