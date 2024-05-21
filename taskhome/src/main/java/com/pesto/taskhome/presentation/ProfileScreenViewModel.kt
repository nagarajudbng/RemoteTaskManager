package com.pesto.taskhome.presentation

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pesto.taskhome.domain.model.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private var sharedPreferences: SharedPreferences
) : ViewModel() {

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
                val name = sharedPreferences.getString("userName","")
                val email = sharedPreferences.getString("email","")
                val image = sharedPreferences.getString("image","")
                _state.update {
                    it.copy(
                        name = name.toString(),
                        image = image,
                        email = email.toString(),
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
        sharedPreferences.edit().putString("image", uri).apply()
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
