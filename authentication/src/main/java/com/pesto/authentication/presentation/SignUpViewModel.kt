package com.pesto.authentication.presentation


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pesto.authentication.data.model.SignIn
import com.pesto.authentication.domain.usecase.ConfirmPasswordValidationUseCase
import com.pesto.authentication.domain.usecase.EmailValidationUseCase
import com.pesto.authentication.domain.usecase.PasswordValidationUseCase
import com.pesto.authentication.domain.usecase.SignInUseCase
import com.pesto.authentication.domain.usecase.SignUpUseCase
import com.pesto.authentication.domain.usecase.UserNameValidationUseCase
import com.pesto.core.presentation.UiEvent
import com.pesto.core.presentation.UiText
import com.pesto.core.presentation.Validations
import com.single.core.states.StandardTextField
import com.single.core.states.StandardTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Created by Nagaraju on 21/05/24.

@HiltViewModel
class SignUpViewModel @Inject constructor(
    var userNameValidationUseCase: UserNameValidationUseCase,
    var emailValidationUseCase: EmailValidationUseCase,
    var passwordValidationUseCase: PasswordValidationUseCase,
    var confirmPasswordValidationUseCase: ConfirmPasswordValidationUseCase,
    var signUpUseCase: SignUpUseCase,
    var signInUseCase: SignInUseCase
): ViewModel() {
//    private val _userName = mutableStateOf(StandardTextField("nagaraju"))
//    private val _email = mutableStateOf(StandardTextField("nagaraju@gmail.com"))
//    private val _password = mutableStateOf(StandardTextField("12345678"))
//    private val _confirmPassword = mutableStateOf(StandardTextField("12345678"))
    private val _userName = mutableStateOf(StandardTextField(""))
    private val _email = mutableStateOf(StandardTextField(""))
    private val _password = mutableStateOf(StandardTextField(""))
    private val _confirmPassword = mutableStateOf(StandardTextField(""))

    val userName= _userName
    val email= _email
    val password= _password
    val confirmPassword= _confirmPassword

    private val _passwordError = MutableLiveData(false)
    val passwordError: LiveData<Boolean> = _passwordError

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun validatePassword() {
        _passwordError.value = _password.value != _confirmPassword.value
    }

    fun onEvent(event:AuthEvent){
            when(event){
                is AuthEvent.EnteredUserName ->{
                    _userName.value = userName.value.copy(
                        text = event.userName
                    )
                }
                is AuthEvent.EnteredEmail -> {
                    _email.value=email.value.copy(
                        text = event.email
                    )
                }
                is AuthEvent.EnteredPassword -> {
                    _password.value = password.value.copy(
                        text = event.password
                    )
                }
                is AuthEvent.EnteredConfirmPassword -> {
                    _confirmPassword.value = confirmPassword.value.copy(
                        text = event.confirmPassword
                    )
                }
                is AuthEvent.SignUP->{
                    viewModelScope.launch {
                        val userNameResult = userNameValidationUseCase(_userName.value.text)
                        var status:String = ""
                        status = when(userNameResult){
                            Validations.USERNAME_TOO_LONG -> "User Name Too long it should below 10 characters"

                            Validations.USERNAME_TOO_SHORT ->"User Name Too short it should above 5 characters"

                            else ->{""}
                        }
                        _userName.value = userName.value.copy(
                            statusText = status
                        )
                        val emailResult = emailValidationUseCase(_email.value.text)
                        status = when(emailResult){
                            Validations.EMAIL_NOT_VALID ->"Email Not Valid"
                            else ->{""}
                        }
                        _email.value = email.value.copy(
                            statusText = status
                        )
                        val passwordResult = passwordValidationUseCase(_password.value.text)

                        status = when(passwordResult){
                            Validations.PASSWORD_TOO_LONG -> "Password to long it should below 15 characters"
                            Validations.PASSWORD_TOO_SHORT ->"Password to short it should above 8 characters"
                            else -> {""}
                        }
                        _password.value = password.value.copy(
                            statusText = status
                        )
                        val confirmResult = confirmPasswordValidationUseCase(_password.value.text,_confirmPassword.value.text)
                        status = when(confirmResult){
                            Validations.PASSWORD_TOO_LONG -> "Password to long it should below 15 characters"
                            Validations.PASSWORD_TOO_SHORT ->"Password to short it should above 8 characters"
                            Validations.PASSWORD_NOT_MATCH ->"Password & Confirm Password not matched"
                            else -> {""}
                        }
                        _confirmPassword.value = confirmPassword.value.copy(
                            statusText = status
                        )
                        if(userNameResult == Validations.USERNAME_VALID
                            && emailResult == Validations.EMAIL_VALID
                            && passwordResult == Validations.PASSWORD_VALID
                            && confirmResult == Validations.PASSWORD_VALID){
                            val signUpResult = signUpUseCase(
                                _userName.value.text,
                                _email.value.text,
                                _password.value.text
                            )
                            if(signUpResult.isAlreadyExists){
                                _eventFlow.emit(UiEvent.ShowSnackBar(UiText.DynamicString("Already Exists")))
                            } else if(signUpResult.isValid){
                                _eventFlow.emit(UiEvent.NavigateUp("Success"))
                            }
                        }
                    }
                }

                is AuthEvent.SignIn -> {
                    viewModelScope.launch {
                        val userNameResult = userNameValidationUseCase(_userName.value.text)
                        var status:String = ""
                        status = when(userNameResult){
                            Validations.USERNAME_TOO_LONG -> "User Name Too long it should below 10 characters"

                            Validations.USERNAME_TOO_SHORT ->"User Name Too short it should above 5 characters"

                            else ->{""}
                        }
                        _userName.value = userName.value.copy(
                            statusText = status
                        )
                        val passwordResult = passwordValidationUseCase(_password.value.text)
                        status = when(passwordResult){
                            Validations.PASSWORD_TOO_LONG -> "Password to long it should below 15 characters"
                            Validations.PASSWORD_TOO_SHORT ->"Password to short it should above 8 characters"
                            else -> {""}
                        }
                        _password.value = password.value.copy(
                            statusText = status
                        )
                        if(userNameResult == Validations.USERNAME_VALID
                            && passwordResult == Validations.PASSWORD_VALID){
                            val singInResult = signInUseCase(
                                _userName.value.text,
                                _password.value.text
                            )
                            if(singInResult.isValid){
                                _eventFlow.emit(UiEvent.NavigateUp("Success"))
                            }
                        }
                    }
                }
            }
    }


}
