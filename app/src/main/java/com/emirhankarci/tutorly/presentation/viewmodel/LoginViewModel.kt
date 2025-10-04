package com.emirhankarci.tutorly.presentation.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhankarci.tutorly.data.repository.AuthRepositoryImpl
import com.emirhankarci.tutorly.domain.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSignedIn: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        viewModelScope.launch {
            val isSignedIn = authRepository.isUserSignedIn()
            val isTokenValid = authRepository.isTokenValid()
            _uiState.value = _uiState.value.copy(
                isSignedIn = isSignedIn && isTokenValid
            )
        }
    }

    fun signInWithGoogle() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val success = authRepository.signInWithGoogle()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSignedIn = success,
                    errorMessage = if (!success) "Sign in failed. Please try again." else null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "An unexpected error occurred"
                )
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _uiState.value = LoginUiState()
        }
    }

    fun deleteAccount(onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val success = authRepository.deleteAccount()
                if (success) {
                    _uiState.value = LoginUiState()
                    onSuccess()
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Hesap silinemedi. Lütfen tekrar deneyin."
                    )
                    onError()
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Hesap silinirken bir hata oluştu"
                )
                onError()
            }
        }
    }

    fun startGoogleSignIn(onIntentReady: (Intent) -> Unit) {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null
        )

        // Create a fresh Google Sign-In client to ensure account picker is shown
        viewModelScope.launch {
            try {
                val freshClient = authRepositoryImpl.createFreshGoogleSignInClient()
                val signInIntent = freshClient.signInIntent
                onIntentReady(signInIntent)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to initialize sign-in: ${e.message}"
                )
            }
        }
    }

    fun signInWithGoogleAccount(account: GoogleSignInAccount) {
        viewModelScope.launch {
            try {
                val success = authRepositoryImpl.firebaseAuthWithGoogle(account)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSignedIn = success,
                    errorMessage = if (!success) "Firebase authentication failed" else null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "An unexpected error occurred"
                )
            }
        }
    }

    fun handleSignInError(errorMessage: String) {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            errorMessage = errorMessage
        )
    }
}