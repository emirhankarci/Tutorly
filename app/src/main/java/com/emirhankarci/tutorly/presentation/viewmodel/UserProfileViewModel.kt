package com.emirhankarci.tutorly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhankarci.tutorly.core.utils.GreetingUtils
import com.emirhankarci.tutorly.domain.entity.AppUser
import com.emirhankarci.tutorly.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserProfileUiState(
    val user: AppUser = AppUser.empty(),
    val isLoading: Boolean = false,
    val greeting: String = ""
)

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState: StateFlow<UserProfileUiState> = _uiState.asStateFlow()

    init {
        loadCurrentUser()
    }

    fun loadCurrentUser() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val user = authRepository.getCurrentAppUser() ?: AppUser.empty()
                val greeting = GreetingUtils.getFullGreeting(user.greetingName, useTurkish = true)

                _uiState.value = _uiState.value.copy(
                    user = user,
                    greeting = greeting,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    user = AppUser.empty(),
                    greeting = GreetingUtils.getFullGreeting("there", useTurkish = true),
                    isLoading = false
                )
            }
        }
    }

    fun refreshUser() {
        loadCurrentUser()
    }

    fun updateGreeting() {
        val currentUser = _uiState.value.user
        val newGreeting = GreetingUtils.getFullGreeting(currentUser.greetingName, useTurkish = true)
        _uiState.value = _uiState.value.copy(greeting = newGreeting)
    }
}