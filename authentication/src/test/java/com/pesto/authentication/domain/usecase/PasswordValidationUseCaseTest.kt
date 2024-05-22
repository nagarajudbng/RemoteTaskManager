package com.pesto.authentication.domain.usecase

import com.pesto.authentication.domain.repository.SignUpRepository
import com.pesto.core.presentation.Validations
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

// Created by Nagaraju Deshetty on 07/05/24.
class PasswordValidationUseCaseTest{

    @InjectMocks
    private lateinit var passwordValidationUseCase: PasswordValidationUseCase

    @Mock
    private lateinit var repository: SignUpRepository

    @Before
    fun startUP(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testPasswordTooLong()= runBlocking {
        val password = "1234567890123456"
        val result = passwordValidationUseCase(password)
        assertEquals(Validations.PASSWORD_TOO_LONG,result)
    }
    @Test
    fun testPasswordTooShort()= runBlocking {
        val password = "abce"
        val result = passwordValidationUseCase(password)
        assertEquals(Validations.PASSWORD_TOO_SHORT,result)
    }
    @Test
    fun testPasswordValid()= runBlocking {
        val password = "abcedefgh"
        val result = passwordValidationUseCase(password)
        assertEquals(Validations.PASSWORD_VALID,result)
    }
}
