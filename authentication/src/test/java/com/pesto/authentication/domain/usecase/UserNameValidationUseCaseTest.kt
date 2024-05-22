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
class UserNameValidationUseCaseTest{

    @InjectMocks
    private lateinit var userNameValidationUseCase: UserNameValidationUseCase

    @Mock
    private lateinit var repository: SignUpRepository

    @Before
    fun startUP(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testUserNameTooLong()= runBlocking {
        val userName = "abcedefghijklmn"
        val result = userNameValidationUseCase(userName)
        assertEquals(Validations.USERNAME_TOO_LONG,result)
    }
    @Test
    fun testUserNameTooShort()= runBlocking {
        val userName = "abce"
        val result = userNameValidationUseCase(userName)
        assertEquals(Validations.USERNAME_TOO_SHORT,result)
    }
    @Test
    fun testUserNameValid()= runBlocking {
        val userName = "abcedefgh"
        val result = userNameValidationUseCase(userName)
        assertEquals(Validations.USERNAME_VALID,result)
    }
}
