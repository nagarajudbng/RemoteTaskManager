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
class EmailValidationUseCaseTest{

    @InjectMocks
    private lateinit var emailValidationUseCase: EmailValidationUseCase

    @Mock
    private lateinit var repository: SignUpRepository

    @Before
    fun startUP(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testEmailValidationWithValidEmailFail()= runBlocking {
        val email = "abc@xyz"
        val result = emailValidationUseCase(email)
        assertEquals(Validations.EMAIL_NOT_VALID,result)
    }
    @Test
    fun testEmailValidationWithValidEmailSuccess()= runBlocking {
        val email = "abc@xyz.com"
        val result = emailValidationUseCase(email)
        assertEquals(Validations.EMAIL_VALID,result)
    }
}
