package com.pesto.profile.presentation

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pesto.core.presentation.UiEvent
import com.pesto.core.presentation.UiText
import com.pesto.core.presentation.Validations
import com.pesto.profile.domain.usecase.EmailValidationUseCase
import com.pesto.profile.domain.usecase.ProfileGetUseCase
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
    private var profileSaveUseCase: ProfileSaveUseCase
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
                        profileSaveUseCase(
                            id.value,
                            _userName.value.text,
                            _email.value.text,
                            _imageURI.value.uri!!
                        )
//                        val signUpResult = profileUseCase(
//                            _userName.value.text,
//                            _email.value.text,
//                        )
//                        if(signUpResult.isAlreadyExists){
//                            _eventFlow.emit(UiEvent.ShowSnackBar(UiText.DynamicString("Already Exists")))
//                        } else if(signUpResult.isValid){
//                            _eventFlow.emit(UiEvent.NavigateUp("Success"))
//                        }
                    }
                }
            }
        }
    }

   private fun load() {
        viewModelScope.launch {
            val profileDomain = profileGetUseCase()
            val profiles = listOf( ProfileUI(1L,"","",""))

            if (profiles.isNotEmpty()) {
                _id.value = profileDomain.id
                _userName.value.text = profileDomain.name
                _email.value.text = profileDomain.email
                _imageURI.value.uri = profileDomain.image.toUri()
//                val profile = profiles.first()
//                val name = sharedPreferences.getString("userName","")
//                val email = sharedPreferences.getString("email","")
//                val image = sharedPreferences.getString("image","")
//                _state.update {
//                    it.copy(
//                        name = name.toString(),
//                        image = image,
//                        email = email.toString(),
//                        id = profile.id
//                    )
//                }

//                setProfileImageUri(profile.image)
            }

        }
    }

}
