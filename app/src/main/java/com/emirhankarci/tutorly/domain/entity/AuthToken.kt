package com.emirhankarci.tutorly.domain.entity

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class AuthToken(
    val accessToken: String = "",
    val refreshToken: String = "",
    val idToken: String = "",
    val expiresAt: Long = 0L, // Unix timestamp
    val tokenType: String = "Bearer",
    val scope: String = ""
) {
    companion object {
        fun empty() = AuthToken()
    }

    val isExpired: Boolean
        get() = System.currentTimeMillis() >= expiresAt

    val isExpiringSoon: Boolean
        get() = System.currentTimeMillis() >= (expiresAt - 5 * 60 * 1000) // 5 minutes before expiry

    val isValid: Boolean
        get() = accessToken.isNotEmpty() && !isExpired

    val expiresInMinutes: Long
        get() = if (isExpired) 0 else (expiresAt - System.currentTimeMillis()) / (60 * 1000)
}

@Serializable
data class TokenRefreshRequest(
    val refreshToken: String,
    val grantType: String = "refresh_token"
)

@Serializable
data class TokenRefreshResponse(
    val accessToken: String,
    val refreshToken: String? = null, // Some services don't return a new refresh token
    val idToken: String? = null,
    val expiresIn: Long, // Seconds until expiry
    val tokenType: String = "Bearer",
    val scope: String? = null
)