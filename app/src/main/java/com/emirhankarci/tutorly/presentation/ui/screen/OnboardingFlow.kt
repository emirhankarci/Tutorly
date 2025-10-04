package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.emirhankarci.tutorly.R
import com.emirhankarci.tutorly.presentation.viewmodel.LoginViewModel

data class OnboardingPageData(
    val lottieRes: Int,
    val title: String,
    val description: String,
    val gradientColors: List<Color>,
    val animationSpeed: Float = 1f
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingFlow(
    modifier: Modifier = Modifier,
    onSignInSuccess: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val onboardingPages = remember {
        listOf(
            OnboardingPageData(
                lottieRes = R.raw.robotteach,
                title = "Her An Yanında Bir Öğretmen",
                description = "Zorlandığın konuları sor, yapay zekâ sana kolay ve anlaşılır şekilde anlatsın",
                gradientColors = listOf(
                    Color(0xFF6B4CE6),
                    Color(0xFF9C6FFF),
                    Color(0xFFD4AAFF)
                ),
                animationSpeed = 0.6f
            ),
            OnboardingPageData(
                lottieRes = R.raw.artificalintelligence,
                title = "Ders Çalışmanın Akıllı Yolu",
                description = "Yapay zekâ destekli çalışma arkadaşınla öğrenmeyi daha kolay, eğlenceli ve verimli hale getir.",
                gradientColors = listOf(
                    Color(0xFF2D5016),
                    Color(0xFF4A7C23),
                    Color(0xFF6B9C35)
                ),
                animationSpeed = 1f
            ),
            OnboardingPageData(
                lottieRes = R.raw.examprep,
                title = "Kendi Quizlerini Hazırla",
                description = "Yapay zekâ senin için testler ve görsel sorular oluştursun, kendini dene ve bilgini pekiştir",
                gradientColors = listOf(
                    Color(0xFFE67E22),
                    Color(0xFFF39C12),
                    Color(0xFFFFA94D)
                ),
                animationSpeed = 0.4f
            ),
            OnboardingPageData(
                lottieRes = R.raw.schedule,
                title = "Kişiselleştirmiş Ders Programını Hazırla",
                description = "Yapay zeka yardımı ile veya kendin kişiselleştirilmiş ders programını hazırla.",
                gradientColors = listOf(
                    Color(0xFF27AE60),
                    Color(0xFF52BE80),
                    Color(0xFFF39C12),
                    Color(0xFFE67E22),
                    Color(0xFF58D68D)
                ),
                animationSpeed = 0.5f
            )
        )
    }

    val pagerState = rememberPagerState(pageCount = { onboardingPages.size + 1 }) // 4 onboarding + 1 login
    val scope = rememberCoroutineScope()

    // Animate gradient colors based on current page
    val currentGradient = remember(pagerState.currentPage) {
        if (pagerState.currentPage < onboardingPages.size) {
            onboardingPages[pagerState.currentPage].gradientColors
        } else {
            listOf(Color(0xFF1A1A2E), Color(0xFF16213E), Color(0xFF0F3460))
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Animated gradient background with pulsing effect
        AnimatedContent(
            targetState = pagerState.currentPage,
            transitionSpec = {
                fadeIn(tween(1000)) togetherWith fadeOut(tween(1000))
            },
            label = "gradient"
        ) { page ->
            val gradient = if (page < onboardingPages.size) {
                onboardingPages[page].gradientColors
            } else {
                listOf(Color(0xFF1A1A2E), Color(0xFF16213E), Color(0xFF0F3460))
            }
            
            // Infinite pulsing animation for background
            val infiniteTransition = rememberInfiniteTransition(label = "backgroundPulse")
            val pulseAlpha by infiniteTransition.animateFloat(
                initialValue = 0.7f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "pulseAlpha"
            )
            
            val offsetAnimation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(8000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "gradientOffset"
            )
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = gradient.map { it.copy(alpha = pulseAlpha) },
                            startY = offsetAnimation * 300f,
                            endY = 1000f + offsetAnimation * 500f
                        )
                    )
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                when (page) {
                    in 0 until onboardingPages.size -> {
                        OnboardingPage(
                            pageData = onboardingPages[page],
                            isLastPage = page == onboardingPages.size - 1,
                            onSkip = {
                                scope.launch {
                                    pagerState.animateScrollToPage(onboardingPages.size) // Go to login page
                                }
                            },
                            onNext = {
                                scope.launch {
                                    pagerState.animateScrollToPage(page + 1)
                                }
                            }
                        )
                    }
                    else -> {
                        LoginScreen(
                            onSignInSuccess = onSignInSuccess,
                            viewModel = viewModel
                        )
                    }
                }
            }

            // Auto-advance to next page every 10 seconds (onboarding pages only)
            LaunchedEffect(pagerState.currentPage) {
                val current = pagerState.currentPage
                if (current < onboardingPages.size) {
                    delay(8_000)
                    scope.launch {
                        val next = current + 1
                        val target = if (next <= onboardingPages.size) next else onboardingPages.size
                        pagerState.animateScrollToPage(target)
                    }
                }
            }

            // Page indicators - only show for onboarding pages (not login)
            if (pagerState.currentPage < onboardingPages.size) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(onboardingPages.size) { iteration ->
                        val color = if (pagerState.currentPage == iteration) {
                            Color.White
                        } else {
                            Color.White.copy(alpha = 0.4f)
                        }
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 24.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(if (pagerState.currentPage == iteration) 12.dp else 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OnboardingPage(
    pageData: OnboardingPageData,
    isLastPage: Boolean,
    onSkip: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Skip button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = onSkip,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.White.copy(alpha = 0.8f)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Atla",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Atla",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Lottie Animation with AnimatedContent
        AnimatedContent(
            targetState = pageData.lottieRes,
            transitionSpec = {
                (fadeIn(animationSpec = tween(600)) + 
                 scaleIn(initialScale = 0.7f, animationSpec = tween(600)) +
                 slideInVertically(animationSpec = tween(600)) { it / 3 })
                    .togetherWith(
                        fadeOut(animationSpec = tween(400)) + 
                        scaleOut(targetScale = 0.7f, animationSpec = tween(400))
                    )
            },
            label = "lottieAnimation"
        ) { lottieRes ->
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRes))
            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                isPlaying = true,
                speed = pageData.animationSpeed,
                restartOnPlay = false
            )

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Typewriter effect for title
        TypewriterText(
            text = pageData.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description text with animation
        AnimatedContent(
            targetState = pageData.description,
            transitionSpec = {
                (fadeIn(tween(1000, delayMillis = 300)) + 
                 slideInVertically(tween(1000, delayMillis = 300)) { it / 2 })
                    .togetherWith(
                        fadeOut(tween(400)) + 
                        slideOutVertically(tween(400)) { -it / 2 }
                    )
            },
            label = "description"
        ) { description ->
            TypewriterText(
                text = description,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                ),
                modifier = Modifier.padding(horizontal = 16.dp),
                typingDelayMs = 20
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Animated button appearance
        var buttonVisible by remember { mutableStateOf(false) }
        
        LaunchedEffect(pageData.lottieRes) {
            buttonVisible = false
            delay(800)
            buttonVisible = true
        }

        AnimatedVisibility(
            visible = buttonVisible,
            enter = fadeIn(tween(600)) + 
                    scaleIn(initialScale = 0.8f, animationSpec = tween(600)) +
                    slideInVertically(tween(600)) { it / 2 }
        ) {
            // Next button
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = pageData.gradientColors.first()
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
                )
            ) {
                Text(
                    text = if (isLastPage) "Başla" else "İleri",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                if (!isLastPage) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun TypewriterText(
    text: String,
    style: androidx.compose.ui.text.TextStyle,
    modifier: Modifier = Modifier,
    typingDelayMs: Long = 50
) {
    var displayedText by remember(text) { mutableStateOf("") }

    LaunchedEffect(text) {
        displayedText = ""
        text.forEachIndexed { index, _ ->
            displayedText = text.substring(0, index + 1)
            delay(typingDelayMs)
        }
    }

    Text(
        text = displayedText,
        style = style,
        modifier = modifier
    )
}