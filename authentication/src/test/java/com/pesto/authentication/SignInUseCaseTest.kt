package com.pesto.authentication

import com.pesto.authentication.domain.model.SignInDomain
import com.pesto.authentication.domain.model.SignUpDomain
import com.pesto.authentication.domain.repository.SignUpRepository
import com.pesto.authentication.domain.usecase.SignInUseCase
import com.pesto.authentication.domain.usecase.SignUpUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


// Created by Nagaraju on 22/05/24.

class SignInUseCaseTest{

    @Mock
    private lateinit var  signUpRepository: SignUpRepository;

    @InjectMocks
    private lateinit var  singInUseCase: SignInUseCase

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    fun testSignInSuccess(): Unit = runBlocking {
        val userName = "testUser"
        val password = "password"
        val signInDomain = SignInDomain(isValid = true)

        `when`(signUpRepository.signIn(userName, password)).thenReturn(signInDomain)
        val result = singInUseCase(userName, password)
        verify(signUpRepository).signIn(userName, password)
        assertEquals(signInDomain, result)
    }
    @Test
    fun testSignInFail(): Unit = runBlocking {
        val userName = "testUser"
        val email = "test@example.com"
        val password = "password"
        val signInDomain = SignInDomain(isValid = false)

        `when`(signUpRepository.signIn(userName,  password)).thenReturn(signInDomain)
        val result = singInUseCase(userName, password)
        verify(signUpRepository).signIn(userName, password)
        assertEquals(signInDomain, result)
    }

}