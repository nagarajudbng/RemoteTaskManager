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
class ConfirmPasswordValidationUseCaseTest{

    @InjectMocks
    private lateinit var confirmPasswordValidationUseCase: ConfirmPasswordValidationUseCase

    @Mock
    private lateinit var repository: SignUpRepository

    @Before
    fun startUP(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testUserNameTooLong()= runBlocking {

        val password = "12345678"
        val confirmPassword = "1234567890123456"
        val result = confirmPasswordValidationUseCase(password,confirmPassword)
        assertEquals(Validations.PASSWORD_TOO_LONG,result)
    }
    @Test
    fun testConfirmPasswordTooShort()= runBlocking {
        val password = "abce"
        val confirmPassword = "1234"
        val result = confirmPasswordValidationUseCase(password,confirmPassword)
        assertEquals(Validations.PASSWORD_TOO_SHORT,result)
    }
    @Test
    fun testConfirmPasswordNotMatch()= runBlocking {
        val password = "abcedefgh"
        val confirmPassword = "123456789"
        val result = confirmPasswordValidationUseCase(password,confirmPassword)
        assertEquals(Validations.PASSWORD_NOT_MATCH,result)
    }
    @Test
    fun testConfirmPasswordValid()= runBlocking {
        val password = "abcedefgh"
        val confirmPassword = "abcedefgh"
        val result = confirmPasswordValidationUseCase(password,confirmPassword)
        assertEquals(Validations.PASSWORD_VALID,result)
    }
}
