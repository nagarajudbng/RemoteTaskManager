package com.pesto.profile.presentation

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pesto.core.presentation.UiEvent
import com.pesto.core.presentation.UiText
import com.pesto.core.presentation.Validations
import com.pesto.profile.domain.model.LogoutResponse
import com.pesto.profile.domain.model.Response
import com.pesto.profile.domain.usecase.EmailValidationUseCase
import com.pesto.profile.domain.usecase.ProfileGetUseCase
import com.pesto.profile.domain.usecase.ProfileLogoutUseCase
import com.pesto.profile.domain.usecase.ProfileSaveUseCase
import com.pesto.profile.domain.usecase.UserNameValidationUseCase
import com.pesto.profile.presentation.model.ProfileUI
import com.single.core.states.StandardImage
import com.single.core.states.StandardTextField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserProfileState(
    val id: Long = 0,
    val name: String = "",
    val email: String = "",
    val image: String? = null
)

//class ProfileViewModel(private val database: AppDatabase) : ViewModel() {
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private var userNameValidationUseCase: UserNameValidationUseCase,
    private var emailValidationUseCase: EmailValidationUseCase,
   private var profileGetUseCase: ProfileGetUseCase,
    private var profileSaveUseCase: ProfileSaveUseCase,
    private var profileLogoutUseCase: ProfileLogoutUseCase

) : ViewModel() {

    private val _state = MutableStateFlow(UserProfileState())
    val state: StateFlow<UserProfileState> = _state

    private val _id = mutableStateOf(0L)
    val id= _id
    private val _userName = mutableStateOf(StandardTextField("nagaraju"))
    val userName= _userName
    private val _email = mutableStateOf(StandardTextField("nagaraju@gmail.com"))
    val email= _email
    private val _imageURI = mutableStateOf(StandardImage())
    val imageURI= _imageURI

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _dialogState = mutableStateOf(false)
    val dialogState = _dialogState


    init {
        load()
    }
    fun onEvent(event:ProfileEvent){
        when(event){
            is ProfileEvent.EnteredUserName ->{
                _userName.value = userName.value.copy(
                    text = event.userName
                )
            }
            is ProfileEvent.EnteredEmail -> {
                _email.value=email.value.copy(
                    text = event.email
                )
            }
            is ProfileEvent.EnteredImageURI -> {
                _imageURI.value=imageURI.value.copy(
                    uri = event.uri
                )
            }
            is ProfileEvent.Logout -> {
                viewModelScope.launch {
                    _dialogState.value = true
                    when (profileLogoutUseCase()) {
                        LogoutResponse.Success -> {
                            _eventFlow.emit(UiEvent.NavigateUp("Success"))
                            _dialogState.value = false
                        }

                        LogoutResponse.Fail -> {
                            _eventFlow.emit(UiEvent.ShowSnackBar(UiText.DynamicString("Something went wrong")))
                            _dialogState.value = false
                        }
                    }
                }
            }
            is ProfileEvent.Save->{
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

                    if(userNameResult == Validations.USERNAME_VALID
                        && emailResult == Validations.EMAIL_VALID)
                    {
                        _dialogState.value = true
                        val updateResult = profileSaveUseCase(
                            id.value,
                            _userName.value.text,
                            _email.value.text,
                            _imageURI.value.uri!!
                        )
                        when(updateResult){
                            is Response.Loading -> _dialogState.value = true
                            is Response.Success -> {
                                _dialogState.value = false
                            }

                            is Response.Failure -> _dialogState.value = false
                        }
                    }
                }
            }

            else -> {}
        }
    }

   private fun load() {
        viewModelScope.launch {
            val profileDomain = profileGetUseCase()
            Log.d("Profile home",profileDomain.toString())
                _id.value = profileDomain.id
                _userName.value.text = profileDomain.name
                _email.value.text = profileDomain.email
                _imageURI.value = imageURI.value.copy(
                   uri = profileDomain.image.toUri()
                )

        }
    }

}
