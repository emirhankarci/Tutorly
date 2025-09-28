package com.emirhankarci.tutorly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhankarci.tutorly.domain.entity.UserProfile
import com.emirhankarci.tutorly.domain.repository.AuthRepository
import com.emirhankarci.tutorly.domain.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileBuildingUiState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isCompleted: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class ProfileBuildingViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileBuildingUiState())
    val uiState: StateFlow<ProfileBuildingUiState> = _uiState.asStateFlow()

    fun saveUserProfile(userProfile: UserProfile, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isSaving = true,
                errorMessage = null
            )

            try {
                val currentUser = authRepository.getCurrentUser()
                if (currentUser != null) {
                    val result = userProfileRepository.saveUserProfile(currentUser.uid, userProfile)

                    if (result.isSuccess) {
                        _uiState.value = _uiState.value.copy(
                            isSaving = false,
                            isCompleted = true
                        )
                        // Add a small delay to ensure Firestore write is committed
                        kotlinx.coroutines.delay(500)
                        onSuccess()
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isSaving = false,
                            errorMessage = result.exceptionOrNull()?.message ?: "Failed to save profile"
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        errorMessage = "User not authenticated"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    errorMessage = e.message ?: "An unexpected error occurred"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}