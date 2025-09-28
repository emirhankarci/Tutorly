package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emirhankarci.tutorly.presentation.ui.components.AutoResizeText
import com.emirhankarci.tutorly.presentation.viewmodel.AuthFlowViewModel
import kotlinx.coroutines.delay

private enum class AnimationState {
    START,
    BOUNCE,
    FADE_IN_TEXT
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateToAuth: () -> Unit = {},
    onNavigateToMain: () -> Unit = {},
    onNavigateToProfileBuilding: () -> Unit = {},
    viewModel: AuthFlowViewModel = hiltViewModel()
) {
    var currentState by remember { mutableStateOf(AnimationState.START) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        currentState = AnimationState.BOUNCE
        delay(1000L)
        currentState = AnimationState.FADE_IN_TEXT
        delay(1500L)

        // Navigate based on authentication flow state
        when (uiState.authFlowState) {
            com.emirhankarci.tutorly.presentation.viewmodel.AuthFlowState.NEED_LOGIN -> onNavigateToAuth()
            com.emirhankarci.tutorly.presentation.viewmodel.AuthFlowState.NEED_PROFILE_SETUP -> onNavigateToProfileBuilding()
            com.emirhankarci.tutorly.presentation.viewmodel.AuthFlowState.AUTHENTICATED -> onNavigateToMain()
            com.emirhankarci.tutorly.presentation.viewmodel.AuthFlowState.LOADING -> {
                // Stay on splash screen
            }
        }
    }

    // Logo animation
    val logoScale by animateFloatAsState(
        targetValue = when (currentState) {
            AnimationState.START -> 0.5f
            AnimationState.BOUNCE -> 1.2f
            AnimationState.FADE_IN_TEXT -> 1f
        },
        animationSpec = when (currentState) {
            AnimationState.BOUNCE -> spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
            else -> tween(durationMillis = 300)
        },
        label = "logoScale"
    )

    val logoAlpha by animateFloatAsState(
        targetValue = if (currentState != AnimationState.START) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "logoAlpha"
    )

    // Text animation
    val textAlpha by animateFloatAsState(
        targetValue = if (currentState == AnimationState.FADE_IN_TEXT) 1f else 0f,
        animationSpec = tween(durationMillis = 750),
        label = "textAlpha"
    )

    val textScale by animateFloatAsState(
        targetValue = if (currentState == AnimationState.FADE_IN_TEXT) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "textScale"
    )

    // Gradient brushes
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        )
    )

    val textGradientBrush = Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.primary
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Logo with animation
            Card(
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 32.dp)
                    .graphicsLayer {
                        scaleX = logoScale
                        scaleY = logoScale
                        alpha = logoAlpha
                    },
                shape = androidx.compose.foundation.shape.RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Tutorly Logo",
                        modifier = Modifier.size(60.dp),
                        tint = Color.White
                    )
                }
            }

            // App Name with gradient and animation
            AutoResizeText(
                text = "TUTORLY",
                style = TextStyle(
                    brush = textGradientBrush,
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .graphicsLayer {
                        alpha = textAlpha
                        scaleX = textScale
                        scaleY = textScale
                    }
            )

            // Subtitle
            Text(
                text = "AI-Powered Learning Platform",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp, start = 32.dp, end = 32.dp)
                    .alpha(textAlpha)
            )
        }

        // Loading indicator at bottom
        if (currentState == AnimationState.FADE_IN_TEXT) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .alpha(textAlpha),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.alpha(textAlpha)
                )
            }
        }
    }
}