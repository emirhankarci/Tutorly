package com.emirhankarci.tutorly.domain.repository

import com.emirhankarci.tutorly.domain.entity.AppUser
import com.emirhankarci.tutorly.domain.entity.AuthToken
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signInWithGoogle(): Boolean
    suspend fun signOut()
    suspend fun deleteAccount(): Boolean
    suspend fun getCurrentUser(): FirebaseUser?
    suspend fun getCurrentAppUser(): AppUser?
    suspend fun getCurrentToken(): AuthToken
    suspend fun refreshTokenIfNeeded(): Result<AuthToken>
    suspend fun isUserSignedIn(): Boolean
    suspend fun isTokenValid(): Boolean
    suspend fun clearAllData(): Unit
}