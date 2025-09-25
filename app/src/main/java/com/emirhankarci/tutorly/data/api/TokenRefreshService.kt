package com.emirhankarci.tutorly.data.api

import com.emirhankarci.tutorly.domain.entity.TokenRefreshRequest
import com.emirhankarci.tutorly.domain.entity.TokenRefreshResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenRefreshService {

    @FormUrlEncoded
    @POST("token")
    suspend fun refreshGoogleToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String = "refresh_token"
    ): Response<GoogleTokenRefreshResponse>

    @POST("auth/refresh")
    suspend fun refreshAppToken(
        @Body request: TokenRefreshRequest
    ): Response<TokenRefreshResponse>
}

data class GoogleTokenRefreshResponse(
    val access_token: String,
    val expires_in: Long,
    val token_type: String = "Bearer",
    val scope: String? = null,
    val id_token: String? = null
)