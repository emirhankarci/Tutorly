package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhankarci.tutorly.domain.entity.*
import com.emirhankarci.tutorly.presentation.viewmodel.QuizViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun QuizScreen(
    grade: Int,
    subject: String,
    chapter: String,
    questionCount: Int,
    questionType: String,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    onQuizCompleted: (Int, Int) -> Unit = { _, _ -> } // score, total
) {
    val vm: QuizViewModel = viewModel()
    val ui by vm.uiState.collectAsState()

    LaunchedEffect(grade, subject, chapter, questionCount, questionType) {
        vm.loadQuiz(grade, subject, chapter, questionCount, questionType)
    }

    if (ui.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF6366F1))
        }
        return
    }

    if (ui.errorMessage.isNotBlank()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Quiz yüklenemedi", color = Color(0xFFDC2626))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = { vm.loadQuiz(grade, subject, chapter, questionCount, questionType) }) {
                    Text("Tekrar dene")
                }
            }
        }
        return
    }

    val questions = ui.questions

    var quizState by remember { mutableStateOf(QuizState()) }
    var currentAnswer by remember { mutableStateOf("") }
    var selectedOption by remember { mutableIntStateOf(-1) }
    var showFeedback by remember { mutableStateOf(false) }
    var showExplanation by remember { mutableStateOf(false) }

    if (questions.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Soru bulunamadı", color = Color(0xFF6B7280))
        }
        return
    }

    val currentQuestion = questions[quizState.currentQuestionIndex]
    val isLastQuestion = quizState.currentQuestionIndex == questions.size - 1
    val isFirstQuestion = quizState.currentQuestionIndex == 0

    // Check if current question is answered
    val isAnswered = when (currentQuestion.type) {
        QuestionType.TEST -> selectedOption != -1
        QuestionType.OPEN_ENDED -> currentAnswer.isNotBlank()
    }

    // Show result screen if quiz completed
    if (quizState.isQuizCompleted) {
        QuizResultScreen(
            score = quizState.score,
            totalQuestions = questions.size,
            subject = subject,
            chapter = chapter,
            onBackPressed = onBackPressed,
            onRetakeQuiz = {
                // Reset quiz state
                quizState = QuizState()
                currentAnswer = ""
                selectedOption = -1
                showFeedback = false
                showExplanation = false
            }
        )
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Progress indicator
        QuizProgressIndicator(
            currentQuestion = quizState.currentQuestionIndex + 1,
            totalQuestions = questions.size
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Question card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Question number
                Text(
                    text = "Soru ${quizState.currentQuestionIndex + 1}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6366F1),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Question text
                Text(
                    text = currentQuestion.question,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1F2937),
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Answer options or text field
                when (currentQuestion.type) {
                    QuestionType.TEST -> {
                        currentQuestion.options.forEachIndexed { index, option ->
                            QuizOptionItem(
                                option = option,
                                isSelected = selectedOption == index,
                                showFeedback = showFeedback,
                                isCorrect = index == currentQuestion.correctAnswerIndex,
                                onClick = {
                                    if (!showFeedback) {
                                        selectedOption = index
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                    QuestionType.OPEN_ENDED -> {
                        OutlinedTextField(
                            value = currentAnswer,
                            onValueChange = {
                                if (!showFeedback) {
                                    currentAnswer = it
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Cevabınızı buraya yazın...") },
                            enabled = !showFeedback,
                            minLines = 3,
                            maxLines = 5
                        )
                    }
                }

                // Show explanation if answer is wrong
                if (showExplanation) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Açıklama:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF374151)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = currentQuestion.explanation,
                                fontSize = 14.sp,
                                color = Color(0xFF6B7280),
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Previous button
            Button(
                onClick = {
                    if (quizState.currentQuestionIndex > 0) {
                        // Save current answer before moving
                        if (showFeedback) {
                            val answer = createQuizAnswer(currentQuestion, selectedOption, currentAnswer)
                            quizState.answers[currentQuestion.id] = answer
                        }

                        // Move to previous question
                        quizState = quizState.copy(currentQuestionIndex = quizState.currentQuestionIndex - 1)

                        // Load previous answer
                        val previousQuestion = questions[quizState.currentQuestionIndex]
                        val previousAnswer = quizState.answers[previousQuestion.id]

                        selectedOption = previousAnswer?.selectedOption ?: -1
                        currentAnswer = previousAnswer?.writtenAnswer ?: ""
                        showFeedback = previousAnswer?.isAnswered == true
                        showExplanation = showFeedback && previousAnswer?.isCorrect != true
                    }
                },
                enabled = !isFirstQuestion,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9CA3AF),
                    disabledContainerColor = Color(0xFFE5E7EB)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Önceki")
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Next/Submit button
            Button(
                onClick = {
                    if (!showFeedback && isAnswered) {
                        // Show feedback for current question
                        val isCorrect = when (currentQuestion.type) {
                            QuestionType.TEST -> selectedOption == currentQuestion.correctAnswerIndex
                            QuestionType.OPEN_ENDED -> currentAnswer.trim().isNotEmpty() // For demo, assume all non-empty answers are correct
                        }

                        val answer = createQuizAnswer(currentQuestion, selectedOption, currentAnswer, isCorrect)
                        quizState.answers[currentQuestion.id] = answer

                        // Update score if correct
                        if (isCorrect) {
                            quizState = quizState.copy(score = quizState.score + 1)
                        }

                        showFeedback = true
                        showExplanation = !isCorrect

                    } else if (showFeedback) {
                        if (isLastQuestion) {
                            // Complete quiz
                            quizState = quizState.copy(isQuizCompleted = true)
                        } else {
                            // Move to next question
                            quizState = quizState.copy(currentQuestionIndex = quizState.currentQuestionIndex + 1)

                            // Load next question answer if exists
                            val nextQuestion = questions[quizState.currentQuestionIndex]
                            val nextAnswer = quizState.answers[nextQuestion.id]

                            selectedOption = nextAnswer?.selectedOption ?: -1
                            currentAnswer = nextAnswer?.writtenAnswer ?: ""
                            showFeedback = nextAnswer?.isAnswered == true
                            showExplanation = showFeedback && nextAnswer?.isCorrect != true
                        }
                    }
                },
                enabled = isAnswered,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6366F1),
                    disabledContainerColor = Color(0xFFE5E7EB)
                )
            ) {
                Text(
                    when {
                        !showFeedback -> "Cevapla"
                        isLastQuestion -> "Bitir"
                        else -> "Sonraki"
                    }
                )
                if (showFeedback && !isLastQuestion) {
                    Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Sonraki",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizProgressIndicator(
    currentQuestion: Int,
    totalQuestions: Int
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Soru $currentQuestion / $totalQuestions",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF374151)
            )

            Text(
                text = "${(currentQuestion * 100 / totalQuestions)}% Tamamlandı",
                fontSize = 14.sp,
                color = Color(0xFF6B7280)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = currentQuestion.toFloat() / totalQuestions,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = Color(0xFF6366F1),
            trackColor = Color(0xFFE5E7EB)
        )
    }
}

@Composable
private fun QuizOptionItem(
    option: String,
    isSelected: Boolean,
    showFeedback: Boolean,
    isCorrect: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        showFeedback && isCorrect -> Color(0xFF10B981) // Green for correct answer
        showFeedback && isSelected && !isCorrect -> Color(0xFFEF4444) // Red for wrong selected answer
        isSelected && !showFeedback -> Color(0xFF6366F1).copy(alpha = 0.1f) // Light blue for selected
        else -> Color.White
    }

    val borderColor = when {
        showFeedback && isCorrect -> Color(0xFF10B981)
        showFeedback && isSelected && !isCorrect -> Color(0xFFEF4444)
        isSelected && !showFeedback -> Color(0xFF6366F1)
        else -> Color(0xFFD1D5DB)
    }

    val textColor = when {
        showFeedback && (isCorrect || (isSelected && !isCorrect)) -> Color.White
        else -> Color(0xFF374151)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(2.dp, borderColor, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 2.dp)
    ) {
        Text(
            text = option,
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp,
            color = textColor,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

@Composable
fun QuizResultScreen(
    score: Int,
    totalQuestions: Int,
    subject: String,
    chapter: String,
    onBackPressed: () -> Unit,
    onRetakeQuiz: () -> Unit
) {
    val percentage = (score * 100 / totalQuestions)
    val isGoodScore = percentage >= 70

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Success/Completion icon or emoji
                Text(
                    text = if (isGoodScore) "🎉" else "📚",
                    fontSize = 64.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Quiz Tamamlandı!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "$subject - $chapter",
                    fontSize = 16.sp,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Score display
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isGoodScore) Color(0xFFF0FDF4) else Color(0xFFFEF2F2)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Skorunuz",
                            fontSize = 16.sp,
                            color = Color(0xFF6B7280),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "$score / $totalQuestions",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isGoodScore) Color(0xFF059669) else Color(0xFFDC2626)
                        )

                        Text(
                            text = "%$percentage",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isGoodScore) Color(0xFF047857) else Color(0xFFB91C1C),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Result message
                Text(
                    text = when {
                        percentage >= 90 -> "Mükemmel! Konuyu çok iyi öğrenmişsin! 🌟"
                        percentage >= 70 -> "Harika! İyi bir performans sergledin! 👏"
                        percentage >= 50 -> "Fena değil! Biraz daha çalışma ile daha da iyileştirebilirsin! 📖"
                        else -> "Endişelenme! Konuyu tekrar çalışıp daha iyi hale getirebilirsin! 💪"
                    },
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF374151),
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                // Action buttons
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onRetakeQuiz,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "Quiz'i Tekrar Çöz",
                            modifier = Modifier.padding(vertical = 4.dp),
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = onBackPressed,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "Ana Sayfaya Dön",
                            modifier = Modifier.padding(vertical = 4.dp),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

private fun createQuizAnswer(
    question: QuizQuestion,
    selectedOption: Int,
    writtenAnswer: String,
    isCorrect: Boolean = false
): QuizAnswer {
    return when (question.type) {
        QuestionType.TEST -> QuizAnswer(
            questionId = question.id,
            selectedOption = selectedOption,
            isCorrect = selectedOption == question.correctAnswerIndex,
            isAnswered = true
        )
        QuestionType.OPEN_ENDED -> QuizAnswer(
            questionId = question.id,
            writtenAnswer = writtenAnswer,
            isCorrect = isCorrect, // For demo purposes, we assume non-empty answers are correct
            isAnswered = writtenAnswer.isNotBlank()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    MaterialTheme {
        QuizScreen(
            grade = 10,
            subject = "Matematik",
            chapter = "Fonksiyonlar",
            questionCount = 5,
            questionType = "Test"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizResultScreenPreview() {
    MaterialTheme {
        QuizResultScreen(
            score = 7,
            totalQuestions = 10,
            subject = "Matematik",
            chapter = "Fonksiyonlar",
            onBackPressed = {},
            onRetakeQuiz = {}
        )
    }
}