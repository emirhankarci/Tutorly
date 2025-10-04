package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhankarci.tutorly.domain.entity.ProfileQuestionData
import com.emirhankarci.tutorly.domain.entity.UserProfile
import com.emirhankarci.tutorly.presentation.viewmodel.ProfileBuildingViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProfileBuildingScreen(
    modifier: Modifier = Modifier,
    onProfileCompleted: (UserProfile) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    viewModel: ProfileBuildingViewModel = hiltViewModel()
) {
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedAnswers by remember { mutableStateOf(mutableMapOf<Int, String>()) }
    val uiState by viewModel.uiState.collectAsState()

    val questions = ProfileQuestionData.questions
    val currentQuestion = questions[currentQuestionIndex]
    val isLastQuestion = currentQuestionIndex == questions.size - 1
    val canProceed = selectedAnswers.containsKey(currentQuestion.id)

    Scaffold(
        contentWindowInsets = WindowInsets.statusBars,
        modifier = modifier
            .consumeWindowInsets(WindowInsets.navigationBars)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(paddingValues)
                .padding(16.dp)
        ) {
        // Header with progress
        ProfileBuildingHeader(
            currentQuestion = currentQuestionIndex + 1,
            totalQuestions = questions.size,
            onNavigateBack = if (currentQuestionIndex > 0) {
                { currentQuestionIndex-- }
            } else {
                { onNavigateBack() }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Progress indicator
        LinearProgressIndicator(
            progress = { (currentQuestionIndex + 1).toFloat() / questions.size },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = Color(0xFF667eea),
            trackColor = Color(0xFFE0E7FF),
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Question
        Text(
            text = currentQuestion.question,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2D3748),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Answer options
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(currentQuestion.answers) { answer ->
                AnswerOption(
                    answer = answer,
                    isSelected = selectedAnswers[currentQuestion.id] == answer,
                    onClick = {
                        selectedAnswers = selectedAnswers.toMutableMap().apply {
                            put(currentQuestion.id, answer)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Previous button
            if (currentQuestionIndex > 0) {
                OutlinedButton(
                    onClick = { currentQuestionIndex-- },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF667eea)
                    ),
                    border = null
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Önceki",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Önceki")
                }
            } else {
                Spacer(modifier = Modifier.width(1.dp))
            }

            // Next/Complete button
            Button(
                onClick = {
                    if (isLastQuestion) {
                        val userProfile = createUserProfile(selectedAnswers, questions)
                        viewModel.saveUserProfile(userProfile) {
                            onProfileCompleted(userProfile)
                        }
                    } else {
                        currentQuestionIndex++
                    }
                },
                enabled = canProceed && !uiState.isSaving,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF667eea),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFE0E7FF),
                    disabledContentColor = Color(0xFF9CA3AF)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Text(if (isLastQuestion) "Tamamla" else "Sonraki")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = if (isLastQuestion) Icons.Default.Check else Icons.Default.ArrowForward,
                        contentDescription = if (isLastQuestion) "Tamamla" else "Sonraki",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
    }
}

@Composable
fun ProfileBuildingHeader(
    currentQuestion: Int,
    totalQuestions: Int,
    onNavigateBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF667eea),
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = "Profil Oluştur",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2D3748)
        )

        Text(
            text = "$currentQuestion/$totalQuestions",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF6B7280)
        )
    }
}

@Composable
fun AnswerOption(
    answer: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFE0E7FF) else Color.White
        ),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF667eea))
        } else null,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = answer,
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) Color(0xFF667eea) else Color(0xFF2D3748),
                modifier = Modifier.weight(1f)
            )

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color(0xFF667eea),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

private fun createUserProfile(
    selectedAnswers: Map<Int, String>,
    questions: List<com.emirhankarci.tutorly.domain.entity.ProfileQuestion>
): UserProfile {
    return UserProfile(
        grade = selectedAnswers[1] ?: "",
        targetExams = selectedAnswers[2] ?: "",
        strongSubjects = selectedAnswers[3] ?: "",
        dailyStudyHours = selectedAnswers[4] ?: "",
        englishLevel = selectedAnswers[5] ?: "",
        ageRange = selectedAnswers[6] ?: ""
    )
}