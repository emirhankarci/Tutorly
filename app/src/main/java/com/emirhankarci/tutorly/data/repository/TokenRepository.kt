package com.emirhankarci.tutorly.data.repository

import android.content.Context
import com.emirhankarci.tutorly.R
import com.emirhankarci.tutorly.data.api.GoogleTokenRefreshResponse
import com.emirhankarci.tutorly.data.api.TokenRefreshService
import com.emirhankarci.tutorly.data.manager.TokenManager
import com.emirhankarci.tutorly.domain.entity.AuthToken
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(
    val tokenManager: TokenManager,
    private val firebaseAuth: FirebaseAuth,
    @ApplicationContext private val context: Context
) {

    private val googleTokenService: TokenRefreshService by lazy {
        Retrofit.Builder()
            .baseUrl("https://oauth2.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TokenRefreshService::class.java)
    }

    suspend fun refreshTokenIfNeeded(): Result<AuthToken> = withContext(Dispatchers.IO) {
        try {
            val currentToken = tokenManager.getToken()

            if (currentToken.isValid && !currentToken.isExpiringSoon) {
                return@withContext Result.success(currentToken)
            }

            if (currentToken.refreshToken.isEmpty()) {
                return@withContext Result.failure(Exception("No refresh token available"))
            }

            val refreshResult = refreshGoogleToken(currentToken.refreshToken)

            if (refreshResult.isSuccess) {
                val newToken = refreshResult.getOrNull()!!
                tokenManager.saveToken(newToken)
                Result.success(newToken)
            } else {
                Result.failure(refreshResult.exceptionOrNull() ?: Exception("Token refresh failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun refreshGoogleToken(refreshToken: String): Result<AuthToken> {
        return try {
            val clientId = context.getString(R.string.default_web_client_id)

            // Note: In production, you should store client_secret securely on your backend
            // For Google OAuth, you typically don't use client_secret in mobile apps
            val response = googleTokenService.refreshGoogleToken(
                clientId = clientId,
                clientSecret = "", // Leave empty for mobile apps
                refreshToken = refreshToken
            )

            if (response.isSuccessful) {
                val tokenResponse = response.body()!!
                val newToken = AuthToken(
                    accessToken = tokenResponse.access_token,
                    refreshToken = refreshToken, // Keep the same refresh token
                    idToken = tokenResponse.id_token ?: "",
                    expiresAt = System.currentTimeMillis() + (tokenResponse.expires_in * 1000),
                    tokenType = tokenResponse.token_type,
                    scope = tokenResponse.scope ?: ""
                )
                Result.success(newToken)
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun refreshFirebaseToken(): Result<String> = withContext(Dispatchers.IO) {
        try {
            val user = firebaseAuth.currentUser
                ?: return@withContext Result.failure(Exception("No authenticated user"))

            val result = user.getIdToken(true).result
            val idToken = result.token
                ?: return@withContext Result.failure(Exception("Failed to get ID token"))

            // Update the stored token with the new Firebase ID token
            val currentToken = tokenManager.getToken()
            val updatedToken = currentToken.copy(
                idToken = idToken,
                expiresAt = System.currentTimeMillis() + (60 * 60 * 1000) // 1 hour
            )
            tokenManager.saveToken(updatedToken)

            Result.success(idToken)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun validateAndRefreshToken(): Result<AuthToken> {
        val currentToken = tokenManager.getToken()

        return when {
            currentToken.isValid && !currentToken.isExpiringSoon -> {
                Result.success(currentToken)
            }
            currentToken.refreshToken.isNotEmpty() -> {
                refreshTokenIfNeeded()
            }
            else -> {
                Result.failure(Exception("No valid tokens available"))
            }
        }
    }

    fun clearAllTokens() {
        tokenManager.clearTokens()
    }

    fun getCurrentToken(): AuthToken {
        return tokenManager.getToken()
    }

    fun isAuthenticated(): Boolean {
        return tokenManager.hasValidTokens() && firebaseAuth.currentUser != null
    }
}