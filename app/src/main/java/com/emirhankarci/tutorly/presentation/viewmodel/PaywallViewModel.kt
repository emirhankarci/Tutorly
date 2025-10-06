package com.emirhankarci.tutorly.presentation.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adapty.Adapty
import com.adapty.models.AdaptyPaywallProduct
import com.adapty.models.AdaptyProfile
import com.adapty.utils.AdaptyResult
import com.emirhankarci.tutorly.data.manager.SubscriptionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaywallViewModel @Inject constructor(
    private val subscriptionManager: SubscriptionManager,
    val userProfileRepository: com.emirhankarci.tutorly.domain.repository.UserProfileRepository,
    val authRepository: com.emirhankarci.tutorly.domain.repository.AuthRepository
) : ViewModel() {

    companion object {
        private const val TAG = "PaywallViewModel"
        private const val PLACEMENT_ID = "subscription_screen"
    }

    private val _uiState = MutableStateFlow<PaywallUiState>(PaywallUiState.Loading)
    val uiState: StateFlow<PaywallUiState> = _uiState.asStateFlow()

    private val _subscriptionStatus = MutableStateFlow(false)
    val subscriptionStatus: StateFlow<Boolean> = _subscriptionStatus.asStateFlow()

    private val _products = MutableStateFlow<List<AdaptyPaywallProduct>>(emptyList())
    val products: StateFlow<List<AdaptyPaywallProduct>> = _products.asStateFlow()

    init {
        checkSubscriptionStatus()
        loadProducts()
    }

    /**
     * Check if user has an active subscription
     */
    fun checkSubscriptionStatus() {
        viewModelScope.launch {
            try {
                val hasSubscription = subscriptionManager.hasActiveSubscription()
                _subscriptionStatus.value = hasSubscription
                Log.d(TAG, "Subscription status: $hasSubscription")
            } catch (e: Exception) {
                Log.e(TAG, "Error checking subscription status", e)
                _subscriptionStatus.value = false
            }
        }
    }

    /**
     * Load available products
     */
    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = PaywallUiState.Loading

            try {
                // Get the paywall
                Adapty.getPaywall(PLACEMENT_ID) { paywallResult ->
                    when (paywallResult) {
                        is AdaptyResult.Success -> {
                            val paywall = paywallResult.value
                            Log.d(TAG, "Paywall fetched successfully")

                            // Get products for the paywall
                            Adapty.getPaywallProducts(paywall) { productsResult ->
                                when (productsResult) {
                                    is AdaptyResult.Success -> {
                                        val products = productsResult.value
                                        Log.d(TAG, "Products fetched: ${products.size} products")
                                        _products.value = products
                                        _uiState.value = PaywallUiState.Ready(products)
                                    }
                                    is AdaptyResult.Error -> {
                                        val error = productsResult.error
                                        Log.e(TAG, "Error fetching products", error)
                                        _uiState.value = PaywallUiState.Error("Failed to load products: ${error.message}")
                                    }
                                }
                            }
                        }
                        is AdaptyResult.Error -> {
                            val error = paywallResult.error
                            Log.e(TAG, "Error fetching paywall", error)
                            _uiState.value = PaywallUiState.Error("Failed to load paywall: ${error.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error loading products", e)
                _uiState.value = PaywallUiState.Error("Unexpected error: ${e.message}")
            }
        }
    }

    /**
     * Make a purchase
     */
    fun makePurchase(activity: Activity, product: AdaptyPaywallProduct, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _uiState.value = PaywallUiState.Purchasing

            try {
                Adapty.makePurchase(activity = activity, product = product) { result ->
                    when (result) {
                        is AdaptyResult.Success -> {
                            Log.d(TAG, "Purchase successful")

                            // Refresh subscription status
                            checkSubscriptionStatus()

                            // Check if user now has premium access by getting fresh profile
                            Adapty.getProfile { profileResult ->
                                when (profileResult) {
                                    is AdaptyResult.Success -> {
                                        val profile = profileResult.value
                                        val premiumAccess = profile.accessLevels["premium"]
                                        val hasAccess = premiumAccess?.isActive == true
                                        _subscriptionStatus.value = hasAccess

                                        if (hasAccess) {
                                            _uiState.value = PaywallUiState.PurchaseSuccess
                                            onSuccess()
                                        } else {
                                            Log.w(TAG, "Purchase completed but premium access not active")
                                            _uiState.value = PaywallUiState.Ready(_products.value)
                                            onError("Purchase completed but access not granted. Please try again.")
                                        }
                                    }
                                    is AdaptyResult.Error -> {
                                        // Still consider purchase successful, just can't verify
                                        _uiState.value = PaywallUiState.PurchaseSuccess
                                        onSuccess()
                                    }
                                }
                            }
                        }
                        is AdaptyResult.Error -> {
                            val error = result.error
                            Log.e(TAG, "Purchase failed", error)
                            _uiState.value = PaywallUiState.Ready(_products.value)
                            onError("Purchase failed: ${error.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error during purchase", e)
                _uiState.value = PaywallUiState.Ready(_products.value)
                onError("Unexpected error: ${e.message}")
            }
        }
    }

    /**
     * Restore purchases
     */
    fun restorePurchases(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _uiState.value = PaywallUiState.Purchasing

            try {
                val hasSubscription = subscriptionManager.restorePurchases()
                if (hasSubscription) {
                    // Security: Record purchase in Firestore
                    recordPurchaseInFirestore()

                    _subscriptionStatus.value = true
                    _uiState.value = PaywallUiState.PurchaseSuccess
                    onSuccess()
                } else {
                    _uiState.value = PaywallUiState.Ready(_products.value)
                    onError("No active subscriptions found")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error restoring purchases", e)
                _uiState.value = PaywallUiState.Ready(_products.value)
                onError("Failed to restore purchases: ${e.message}")
            }
        }
    }

    /**
     * Security: Record purchase completion in Firestore
     * This provides a secondary verification independent of Adapty
     */
    fun recordPurchaseInFirestore() {
        viewModelScope.launch {
            try {
                val currentUser = authRepository.getCurrentUser()
                if (currentUser != null) {
                    val result = userProfileRepository.markSubscriptionPurchased(currentUser.uid)
                    if (result.isSuccess) {
                        Log.d(TAG, "Purchase recorded in Firestore for user: ${currentUser.uid}")
                    } else {
                        Log.e(TAG, "Failed to record purchase in Firestore: ${result.exceptionOrNull()?.message}")
                    }
                } else {
                    Log.e(TAG, "Cannot record purchase: No current user")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error recording purchase in Firestore", e)
            }
        }
    }
}

/**
 * UI State for the paywall
 */
sealed class PaywallUiState {
    data object Loading : PaywallUiState()
    data class Ready(val products: List<AdaptyPaywallProduct>) : PaywallUiState()
    data object Purchasing : PaywallUiState()
    data class Error(val message: String) : PaywallUiState()
    data object PurchaseSuccess : PaywallUiState()
}
