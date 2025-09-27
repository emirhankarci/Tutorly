package com.emirhankarci.tutorly.presentation.ui.screen

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.hilt.navigation.compose.hiltViewModel
import com.emirhankarci.tutorly.R
import com.emirhankarci.tutorly.presentation.viewmodel.LoginViewModel

data class OnboardingPageData(
    val imageRes: Int,
    val title: String,
    val description: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingFlow(
    modifier: Modifier = Modifier,
    onSignInSuccess: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = { 4 }) // 3 onboarding + 1 login
    val scope = rememberCoroutineScope()

    val onboardingPages = remember {
        listOf(
            OnboardingPageData(
                imageRes = R.drawable.homescreen_ai_1,
                title = "FAL-AI ile Güçlendirilmiş Eğitim",
                description = "Kişiselleştirilmiş eğitimi, öğrenme tarzınıza ve hızınıza uyum sağlayan gelişmiş yapay zekâ teknolojisiyle deneyimleyin."
            ),
            OnboardingPageData(
                imageRes = R.drawable.homescreen_ai_2,
                title = "Akıllı Çalışma Oturumları",
                description = "Size özel çalışma materyalleri, pratik sorular ve öğrenme yolları için akıllı öneriler alın."
            ),
            OnboardingPageData(
                imageRes = R.drawable.homescreen_ai_3,
                title = "İlerlemeni Takip Et",
                description = "Akademik hedeflerinize ulaşmanıza yardımcı olacak ayrıntılı analizler ve içgörülerle öğrenme yolculuğunuzu izleyin."
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                in 0..2 -> {
                    OnboardingPage(
                        pageData = onboardingPages[page],
                        isLastPage = page == 2,
                        onSkip = {
                            scope.launch {
                                pagerState.animateScrollToPage(3) // Go to login page
                            }
                        },
                        onNext = {
                            scope.launch {
                                pagerState.animateScrollToPage(page + 1)
                            }
                        }
                    )
                }
                3 -> {
                    LoginScreen(
                        onSignInSuccess = onSignInSuccess,
                        viewModel = viewModel
                    )
                }
            }
        }

        // Page indicators - only show for onboarding pages (not login)
        if (pagerState.currentPage < 3) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(3) { iteration ->
                    val color = if (pagerState.currentPage == iteration) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    }
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(if (pagerState.currentPage == iteration) 12.dp else 8.dp)
                    )
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
                    contentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Skip",
                    modifier = Modifier
                        .size(16.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = "Skip",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Image
        Card(
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(pageData.imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Header text
        Text(
            text = pageData.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            lineHeight = 34.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sub text
        Text(
            text = pageData.description,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Next button
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Text(
                text = if (isLastPage) "Get Started" else "Next",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            if (!isLastPage) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}