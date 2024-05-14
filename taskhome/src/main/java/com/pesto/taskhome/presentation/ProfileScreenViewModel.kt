package com.pesto.taskhome.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pesto.taskhome.domain.model.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserProfileState(
    val id: Long = 0,
    val name: String = "",
    val email: String = "",
    val image: String? = null
)

//class ProfileViewModel(private val database: AppDatabase) : ViewModel() {
class ProfileViewModel() : ViewModel() {

    private val _state = MutableStateFlow(UserProfileState())
    val state: StateFlow<UserProfileState> = _state

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
//            val profiles = database.profileDao().getAll()
            val profiles = listOf( Profile(1L,"","",""))

            if (profiles.isNotEmpty()) {
                val profile = profiles.first()

                _state.update {
                    it.copy(
                        name = profile.name,
                        image = null,
                        email = profile.email,
                        id = profile.id
                    )
                }

                setProfileImageUri(profile.image)
            }

        }
    }

    fun setName(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun setEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun setProfileImageUri(uri: String?) {
        _state.update { it.copy(image = uri) }
    }

    fun saveUserProfile() {
        viewModelScope.launch {
            val state = state.value
            var profile = Profile(
                id = state.id,
                name = state.name,
                email = state.email,
                image = state.image
            )

//            database.profileDao().save(profile)
        }
    }
}
