package com.pesto.authentication

import com.pesto.authentication.domain.model.SignUpDomain
import com.pesto.authentication.domain.repository.SignUpRepository
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

class SignUpUseCaseTest{

    @Mock
    private lateinit var  signUpRepository: SignUpRepository;

    @InjectMocks
    private lateinit var  signUpUseCase: SignUpUseCase

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    fun testSignUpSuccess(): Unit = runBlocking {
        val userName = "testUser"
        val email = "test@example.com"
        val password = "password"
        val signUpDomain = SignUpDomain(isValid = true)

        `when`(signUpRepository.signUp(userName, email, password)).thenReturn(signUpDomain)
        val result = signUpUseCase(userName, email, password)
        verify(signUpRepository).signUp(userName, email, password)
        assertEquals(signUpDomain, result)
    }
    @Test
    fun testSignUpFail(): Unit = runBlocking {
        val userName = "testUser"
        val email = "test@example.com"
        val password = "password"
        val signUpDomain = SignUpDomain(isValid = false)

        `when`(signUpRepository.signUp(userName, email, password)).thenReturn(signUpDomain)
        val result = signUpUseCase(userName, email, password)
        verify(signUpRepository).signUp(userName, email, password)
        assertEquals(signUpDomain, result)
    }

}