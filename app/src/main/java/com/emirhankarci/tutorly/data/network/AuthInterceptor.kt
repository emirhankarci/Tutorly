package com.emirhankarci.tutorly.data.network

import com.emirhankarci.tutorly.data.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Skip auth for login/refresh endpoints
        if (shouldSkipAuth(originalRequest)) {
            return chain.proceed(originalRequest)
        }

        // Add access token to request
        val tokenResult = runBlocking {
            tokenRepository.validateAndRefreshToken()
        }

        val request = if (tokenResult.isSuccess) {
            val token = tokenResult.getOrNull()!!
            originalRequest.newBuilder()
                .addHeader("Authorization", "${token.tokenType} ${token.accessToken}")
                .addHeader("Content-Type", "application/json")
                .build()
        } else {
            originalRequest
        }

        val response = chain.proceed(request)

        // Handle 401 Unauthorized - try to refresh token once
        if (response.code == 401 && !response.request.url.pathSegments.contains("refresh")) {
            response.close()

            val refreshResult = runBlocking {
                tokenRepository.refreshTokenIfNeeded()
            }

            if (refreshResult.isSuccess) {
                val newToken = refreshResult.getOrNull()!!
                val newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "${newToken.tokenType} ${newToken.accessToken}")
                    .addHeader("Content-Type", "application/json")
                    .build()

                return chain.proceed(newRequest)
            } else {
                // Refresh failed, clear tokens and redirect to login
                runBlocking {
                    tokenRepository.clearAllTokens()
                }
            }
        }

        return response
    }

    private fun shouldSkipAuth(request: Request): Boolean {
        val path = request.url.pathSegments.joinToString("/")
        return path.contains("auth/login") ||
               path.contains("auth/refresh") ||
               path.contains("token") ||
               request.header("Authorization") != null // Already has auth header
    }
}