package com.emirhankarci.tutorly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhankarci.tutorly.domain.repository.AuthRepository
import com.emirhankarci.tutorly.domain.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AuthFlowState {
    LOADING,
    NEED_LOGIN,
    NEED_PROFILE_SETUP,
    AUTHENTICATED
}

data class AuthFlowUiState(
    val authFlowState: AuthFlowState = AuthFlowState.LOADING,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

@HiltViewModel
class AuthFlowViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthFlowUiState())
    val uiState: StateFlow<AuthFlowUiState> = _uiState.asStateFlow()

    init {
        checkAuthState()
    }

    fun checkAuthState() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val isSignedIn = authRepository.isUserSignedIn()
                val isTokenValid = authRepository.isTokenValid()

                if (isSignedIn && isTokenValid) {
                    val currentUser = authRepository.getCurrentUser()
                    if (currentUser != null) {
                        // Add a small delay to ensure any recent Firestore writes are available
                        kotlinx.coroutines.delay(100)
                        val profileResult = userProfileRepository.isProfileCompleted(currentUser.uid)

                        if (profileResult.isSuccess) {
                            val isProfileCompleted = profileResult.getOrDefault(false)
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                authFlowState = if (isProfileCompleted) {
                                    AuthFlowState.AUTHENTICATED
                                } else {
                                    AuthFlowState.NEED_PROFILE_SETUP
                                }
                            )
                        } else {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                authFlowState = AuthFlowState.NEED_PROFILE_SETUP
                            )
                        }
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            authFlowState = AuthFlowState.NEED_LOGIN
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        authFlowState = AuthFlowState.NEED_LOGIN
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    authFlowState = AuthFlowState.NEED_LOGIN,
                    errorMessage = e.message
                )
            }
        }
    }

    fun onLoginSuccess() {
        checkAuthState()
    }

    fun onProfileCompleted() {
        viewModelScope.launch {
            // Recheck the auth state to ensure profile is actually saved
            checkAuthState()
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}