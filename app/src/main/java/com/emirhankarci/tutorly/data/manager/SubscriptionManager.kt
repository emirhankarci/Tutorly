package com.emirhankarci.tutorly.data.manager

import android.content.Context
import android.util.Log
import com.adapty.Adapty
import com.adapty.models.AdaptyProfile
import com.adapty.utils.AdaptyResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class SubscriptionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val TAG = "SubscriptionManager"
        private const val ACCESS_LEVEL_PREMIUM = "premium"
    }

    /**
     * Check if the user has an active premium subscription
     * This method validates the subscription server-side through Adapty
     */
    suspend fun hasActiveSubscription(): Boolean = suspendCancellableCoroutine { continuation ->
        Adapty.getProfile { result ->
            when (result) {
                is AdaptyResult.Success -> {
                    val profile = result.value
                    val hasAccess = profile.accessLevels[ACCESS_LEVEL_PREMIUM]?.isActive ?: false
                    Log.d(TAG, "Subscription check: hasAccess = $hasAccess")

                    // Check if the subscription is expired
                    val expiresAt = profile.accessLevels[ACCESS_LEVEL_PREMIUM]?.expiresAt
                    if (expiresAt != null) {
                        Log.d(TAG, "Subscription expires at: $expiresAt")
                    }

                    continuation.resume(hasAccess)
                }
                is AdaptyResult.Error -> {
                    val error = result.error
                    Log.e(TAG, "Error checking subscription: ${error.message}", error)
                    // On error, default to no access for security
                    continuation.resume(false)
                }
            }
        }
    }

    /**
     * Get the user's profile from Adapty
     */
    suspend fun getUserProfile(): AdaptyProfile? = suspendCancellableCoroutine { continuation ->
        Adapty.getProfile { result ->
            when (result) {
                is AdaptyResult.Success -> {
                    continuation.resume(result.value)
                }
                is AdaptyResult.Error -> {
                    Log.e(TAG, "Error getting user profile: ${result.error.message}", result.error)
                    continuation.resume(null)
                }
            }
        }
    }

    /**
     * Restore purchases (useful when user logs in on a new device)
     */
    suspend fun restorePurchases(): Boolean = suspendCancellableCoroutine { continuation ->
        Adapty.restorePurchases { result ->
            when (result) {
                is AdaptyResult.Success -> {
                    val profile = result.value
                    val hasAccess = profile.accessLevels[ACCESS_LEVEL_PREMIUM]?.isActive ?: false
                    Log.d(TAG, "Restore purchases: hasAccess = $hasAccess")
                    continuation.resume(hasAccess)
                }
                is AdaptyResult.Error -> {
                    Log.e(TAG, "Error restoring purchases: ${result.error.message}", result.error)
                    continuation.resume(false)
                }
            }
        }
    }

    /**
     * Identify user with Adapty (call this after login)
     */
    fun identifyUser(userId: String) {
        Adapty.identify(userId) { error ->
            if (error != null) {
                Log.e(TAG, "Error identifying user: ${error.message}", error)
            } else {
                Log.d(TAG, "User identified successfully: $userId")
            }
        }
    }

    /**
     * Logout user from Adapty
     */
    fun logoutUser() {
        Adapty.logout { error ->
            if (error != null) {
                Log.e(TAG, "Error logging out user: ${error.message}", error)
            } else {
                Log.d(TAG, "User logged out successfully")
            }
        }
    }

}
