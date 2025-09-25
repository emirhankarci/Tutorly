package com.emirhankarci.tutorly.data.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.emirhankarci.tutorly.domain.entity.AuthToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val PREFS_FILE_NAME = "tutorly_secure_tokens"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_FCM_TOKEN = "fcm_token"
    }

    private val _tokenState = MutableStateFlow(AuthToken.empty())
    val tokenState: Flow<AuthToken> = _tokenState.asStateFlow()

    private val masterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private val encryptedPrefs: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            PREFS_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    init {
        loadStoredToken()
    }

    private fun loadStoredToken() {
        try {
            val tokenJson = encryptedPrefs.getString(KEY_AUTH_TOKEN, null)
            if (tokenJson != null) {
                val token = json.decodeFromString<AuthToken>(tokenJson)
                _tokenState.value = token
            }
        } catch (e: Exception) {
            // If decoding fails, clear stored token
            clearTokens()
        }
    }

    fun saveToken(token: AuthToken) {
        try {
            val tokenJson = json.encodeToString(token)
            encryptedPrefs.edit()
                .putString(KEY_AUTH_TOKEN, tokenJson)
                .apply()
            _tokenState.value = token
        } catch (e: Exception) {
            // Handle save error
        }
    }

    fun getToken(): AuthToken {
        return _tokenState.value
    }

    fun getAccessToken(): String? {
        val token = getToken()
        return if (token.isValid) token.accessToken else null
    }

    fun getRefreshToken(): String? {
        return getToken().refreshToken.takeIf { it.isNotEmpty() }
    }

    fun getIdToken(): String? {
        val token = getToken()
        return if (token.isValid) token.idToken else null
    }

    fun isTokenValid(): Boolean {
        return getToken().isValid
    }

    fun isTokenExpiringSoon(): Boolean {
        return getToken().isExpiringSoon
    }

    fun clearTokens() {
        encryptedPrefs.edit()
            .remove(KEY_AUTH_TOKEN)
            .apply()
        _tokenState.value = AuthToken.empty()
    }

    fun saveFcmToken(fcmToken: String) {
        encryptedPrefs.edit()
            .putString(KEY_FCM_TOKEN, fcmToken)
            .apply()
    }

    fun getFcmToken(): String? {
        return encryptedPrefs.getString(KEY_FCM_TOKEN, null)
    }

    fun updateTokenExpiry(newExpiresAt: Long) {
        val currentToken = getToken()
        val updatedToken = currentToken.copy(expiresAt = newExpiresAt)
        saveToken(updatedToken)
    }

    fun hasValidTokens(): Boolean {
        val token = getToken()
        return token.accessToken.isNotEmpty() && token.refreshToken.isNotEmpty() && token.isValid
    }
}