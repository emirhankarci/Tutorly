package com.emirhankarci.tutorly.presentation.ui.screen

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.adapty.Adapty
import com.adapty.errors.AdaptyError
import com.adapty.models.AdaptyPaywall
import com.adapty.models.AdaptyPaywallProduct
import com.adapty.models.AdaptyProfile
import com.adapty.models.AdaptyPurchaseResult
import com.adapty.ui.AdaptyUI
import com.adapty.ui.listeners.AdaptyUiDefaultEventListener
import com.adapty.utils.AdaptyResult

@Composable
fun AdaptyPaywallScreen(
    onPurchaseSuccess: () -> Unit = {},
    onClose: () -> Unit = {}
) {
    val context = LocalContext.current
    val activity = context as? Activity

    var paywallState by remember { mutableStateOf<PaywallState>(PaywallState.Loading) }

    LaunchedEffect(Unit) {
        // Fetch the paywall from Adapty
        Adapty.getPaywall("subscription_screen") { paywallResult ->
            when (paywallResult) {
                is AdaptyResult.Success -> {
                    val paywall = paywallResult.value
                    Log.d("AdaptyPaywall", "Paywall fetched successfully")

                    // Fetch view configuration
                    AdaptyUI.getViewConfiguration(paywall) { viewConfigResult ->
                        when (viewConfigResult) {
                            is AdaptyResult.Success -> {
                                val viewConfig = viewConfigResult.value
                                Log.d("AdaptyPaywall", "View configuration fetched successfully")

                                // Fetch products for the paywall
                                Adapty.getPaywallProducts(paywall) { productsResult ->
                                    when (productsResult) {
                                        is AdaptyResult.Success -> {
                                            val products = productsResult.value
                                            Log.d("AdaptyPaywall", "Products fetched: ${products.size} products")
                                            paywallState = PaywallState.Success(paywall, viewConfig, products)
                                        }
                                        is AdaptyResult.Error -> {
                                            val error = productsResult.error
                                            Log.e("AdaptyPaywall", "Error fetching products: ${error.message}", error)
                                            paywallState = PaywallState.Error(error.message ?: "Unknown error")
                                        }
                                    }
                                }
                            }
                            is AdaptyResult.Error -> {
                                val error = viewConfigResult.error
                                Log.e("AdaptyPaywall", "Error fetching view configuration: ${error.message}", error)
                                paywallState = PaywallState.Error(error.message ?: "Unknown error")
                            }
                        }
                    }
                }
                is AdaptyResult.Error -> {
                    val error = paywallResult.error
                    Log.e("AdaptyPaywall", "Error fetching paywall: ${error.message}", error)
                    paywallState = PaywallState.Error(error.message ?: "Unknown error")
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val state = paywallState) {
            is PaywallState.Loading -> {
                CircularProgressIndicator()
            }
            is PaywallState.Success -> {
                if (activity != null) {
                    AndroidView(
                        factory = { ctx ->
                            AdaptyUI.getPaywallView(
                                activity = activity,
                                viewConfiguration = state.viewConfiguration,
                                products = state.products,
                                eventListener = object : AdaptyUiDefaultEventListener() {
                                    override fun onActionPerformed(
                                        action: AdaptyUI.Action,
                                        context: Context
                                    ) {
                                        when (action) {
                                            is AdaptyUI.Action.Close -> {
                                                Log.d("AdaptyPaywall", "Close button clicked")
                                                onClose()
                                            }
                                            else -> super.onActionPerformed(action, context)
                                        }
                                    }

                                    override fun onProductSelected(
                                        product: AdaptyPaywallProduct,
                                        context: Context
                                    ) {
                                        Log.d("AdaptyPaywall", "Product selected: ${product.vendorProductId}")
                                    }

                                    override fun onPurchaseStarted(
                                        product: AdaptyPaywallProduct,
                                        context: Context
                                    ) {
                                        Log.d("AdaptyPaywall", "Purchase started: ${product.vendorProductId}")
                                    }

                                    override fun onPurchaseFinished(
                                        purchaseResult: AdaptyPurchaseResult,
                                        product: AdaptyPaywallProduct,
                                        context: Context
                                    ) {
                                        Log.d("AdaptyPaywall", "Purchase successful: ${product.vendorProductId}")
                                        onPurchaseSuccess()
                                    }

                                    override fun onPurchaseFailure(
                                        error: AdaptyError,
                                        product: AdaptyPaywallProduct,
                                        context: Context
                                    ) {
                                        Log.e("AdaptyPaywall", "Purchase failed: ${error.message}", error)
                                    }

                                    override fun onRestoreSuccess(
                                        profile: AdaptyProfile,
                                        context: Context
                                    ) {
                                        Log.d("AdaptyPaywall", "Restore successful")
                                        onPurchaseSuccess()
                                    }

                                    override fun onRestoreFailure(
                                        error: AdaptyError,
                                        context: Context
                                    ) {
                                        Log.e("AdaptyPaywall", "Restore failed: ${error.message}", error)
                                    }

                                    override fun onRenderingError(
                                        error: AdaptyError,
                                        context: Context
                                    ) {
                                        Log.e("AdaptyPaywall", "Rendering error: ${error.message}", error)
                                    }

                                    override fun onLoadingProductsFailure(
                                        error: AdaptyError,
                                        context: Context
                                    ): Boolean {
                                        Log.e("AdaptyPaywall", "Loading products failed: ${error.message}", error)
                                        return false
                                    }
                                }
                            )
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = "Activity context required",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            is PaywallState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Failed to load paywall",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

sealed class PaywallState {
    data object Loading : PaywallState()
    data class Success(
        val paywall: AdaptyPaywall,
        val viewConfiguration: AdaptyUI.LocalizedViewConfiguration,
        val products: List<AdaptyPaywallProduct>
    ) : PaywallState()
    data class Error(val message: String) : PaywallState()
}
