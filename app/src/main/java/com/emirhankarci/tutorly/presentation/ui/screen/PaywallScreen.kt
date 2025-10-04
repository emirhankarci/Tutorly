package com.emirhankarci.tutorly.presentation.ui.screen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adapty.models.AdaptyPaywallProduct
import com.emirhankarci.tutorly.R
import com.emirhankarci.tutorly.presentation.viewmodel.PaywallUiState
import com.emirhankarci.tutorly.presentation.viewmodel.PaywallViewModel

@Composable
fun PaywallScreen(
    onSubscriptionSuccess: () -> Unit,
    viewModel: PaywallViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val subscriptionStatus by viewModel.subscriptionStatus.collectAsState()
    val products by viewModel.products.collectAsState()

    var selectedProductIndex by remember { mutableStateOf(1) } // Default to monthly

    // If user already has subscription, navigate immediately
    LaunchedEffect(subscriptionStatus) {
        if (subscriptionStatus) {
            onSubscriptionSuccess()
        }
    }

    val orangeColor = Color(0xFFFF9D42)
    val darkBlue = Color(0xFF1A2B4A)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (val state = uiState) {
            is PaywallUiState.Loading -> {
                LoadingView()
            }
            is PaywallUiState.Ready -> {
                // Background Image
                Image(
                    painter = painterResource(id = R.drawable.bg_image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.weight(0.3f))

                    // Header Section
                    Text(
                        text = "Potansiyelini\nOrtaya Çıkar",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = darkBlue,
                        textAlign = TextAlign.Center,
                        lineHeight = 38.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Bugün öğrenmeye başlamak için bir plan seç.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    // Plan Cards
                    state.products.forEachIndexed { index, product ->
                        val isSelected = selectedProductIndex == index
                        val isBestValue = index == 1 // Monthly is best value

                        PlanCard(
                            product = product,
                            isSelected = isSelected,
                            orangeColor = orangeColor,
                            darkBlue = darkBlue,
                            showBadge = isBestValue,
                            onClick = { selectedProductIndex = index }
                        )

                        if (index < state.products.size - 1) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Start Learning Button
                    Button(
                        onClick = {
                            val activity = context as? Activity
                            if (activity != null && products.isNotEmpty() && selectedProductIndex < products.size) {
                                viewModel.makePurchase(
                                    activity = activity,
                                    product = products[selectedProductIndex],
                                    onSuccess = {
                                        Toast.makeText(context, "Subscription activated!", Toast.LENGTH_SHORT).show()
                                        onSubscriptionSuccess()
                                    },
                                    onError = { error ->
                                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                                    }
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = orangeColor
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Öğrenmeye Başla",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Restore Purchase Button
                    Button(
                        onClick = {
                            viewModel.restorePurchases(
                                onSuccess = {
                                    Toast.makeText(context, "Purchases restored!", Toast.LENGTH_SHORT).show()
                                    onSubscriptionSuccess()
                                },
                                onError = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD1D5DB)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Satın Alımı Geri Yükle",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.weight(0.2f))
                }
            }
            is PaywallUiState.Purchasing -> {
                PurchasingView()
            }
            is PaywallUiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = {
                        viewModel.checkSubscriptionStatus()
                    }
                )
            }
            is PaywallUiState.PurchaseSuccess -> {
                LoadingView()
            }
        }
    }
}

@Composable
private fun PlanCard(
    product: AdaptyPaywallProduct,
    isSelected: Boolean,
    orangeColor: Color,
    darkBlue: Color,
    showBadge: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Main Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (isSelected) orangeColor.copy(alpha = 0.15f)
                    else Color.White
                )
                .border(
                    width = if (isSelected) 3.dp else 1.dp,
                    color = if (isSelected) orangeColor else Color(0xFFE5E7EB),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable(onClick = onClick)
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = product.localizedTitle ?: "Premium",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = darkBlue
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = product.price.localizedString ?: "",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkBlue
                )
            }
        }

        // Best Value Badge
        if (showBadge) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-16).dp, y = (-8).dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(orangeColor)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "En İyi Değer",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Abonelik seçenekleri yükleniyor...",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun PurchasingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Satın alma işleniyor...",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun ErrorView(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Hata",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onRetry) {
                Text("Tekrar Dene")
            }
        }
    }
}
