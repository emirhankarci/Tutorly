package com.emirhankarci.tutorly.data.repository

import android.content.Context
import com.emirhankarci.tutorly.data.repository.TokenRepository
import com.emirhankarci.tutorly.domain.entity.AppUser
import com.emirhankarci.tutorly.domain.entity.AuthToken
import com.emirhankarci.tutorly.domain.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val tokenRepository: TokenRepository,
    @ApplicationContext private val context: Context
) : AuthRepository {

    private val googleSignInClient: GoogleSignInClient by lazy {
        createGoogleSignInClient()
    }

    private fun createGoogleSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(com.emirhankarci.tutorly.R.string.default_web_client_id))
            .requestServerAuthCode(context.getString(com.emirhankarci.tutorly.R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    suspend fun createFreshGoogleSignInClient(): GoogleSignInClient {
        // Clear the cached account first
        try {
            googleSignInClient.signOut().await()
        } catch (e: Exception) {
            // Ignore sign out errors when creating fresh client
        }

        // Create a new client instance to ensure fresh state
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(com.emirhankarci.tutorly.R.string.default_web_client_id))
            .requestServerAuthCode(context.getString(com.emirhankarci.tutorly.R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

    fun provideGoogleSignInClient(): GoogleSignInClient = googleSignInClient

    suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            val user = result.user

            if (user != null) {
                // Get the ID token from Firebase
                val tokenResult = user.getIdToken(false).await()
                val idToken = tokenResult.token ?: ""

                // Create and save the auth token
                val authToken = AuthToken(
                    accessToken = account.idToken ?: "", // Google ID token as access token
                    refreshToken = account.serverAuthCode ?: "", // Use server auth code as refresh token
                    idToken = idToken,
                    expiresAt = System.currentTimeMillis() + (60 * 60 * 1000), // 1 hour
                    tokenType = "Bearer",
                    scope = "email profile openid"
                )

                // Save the token using TokenRepository's TokenManager
                val tokenManager = tokenRepository.tokenManager
                tokenManager.saveToken(authToken)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun signInWithGoogle(): Boolean {
        // This method will be handled by the Composable using Activity Result API
        // For now, we'll just return false as the actual implementation is in the UI layer
        return false
    }

    override suspend fun signOut() {
        try {
            firebaseAuth.signOut()
            googleSignInClient.signOut().await()
            tokenRepository.clearAllTokens()
        } catch (e: Exception) {
            // Handle sign out error
        }
    }

    override suspend fun deleteAccount(): Boolean {
        return try {
            val user = firebaseAuth.currentUser
            if (user != null) {
                // Delete user from Firebase Authentication
                user.delete().await()
                // Sign out from Google
                googleSignInClient.signOut().await()
                // Clear all tokens and data
                tokenRepository.clearAllTokens()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override suspend fun getCurrentAppUser(): AppUser? {
        val firebaseUser = firebaseAuth.currentUser ?: return null

        return AppUser(
            uid = firebaseUser.uid,
            displayName = firebaseUser.displayName ?: "",
            email = firebaseUser.email ?: "",
            photoUrl = firebaseUser.photoUrl?.toString(),
            firstName = firebaseUser.displayName?.split(" ")?.firstOrNull() ?: "",
            lastName = firebaseUser.displayName?.split(" ")?.drop(1)?.joinToString(" ") ?: ""
        )
    }

    override suspend fun getCurrentToken(): AuthToken {
        return tokenRepository.getCurrentToken()
    }

    override suspend fun refreshTokenIfNeeded(): Result<AuthToken> {
        return tokenRepository.refreshTokenIfNeeded()
    }

    override suspend fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null && tokenRepository.isAuthenticated()
    }

    override suspend fun isTokenValid(): Boolean {
        return tokenRepository.getCurrentToken().isValid
    }

    override suspend fun clearAllData() {
        signOut()
        tokenRepository.clearAllTokens()
    }
}