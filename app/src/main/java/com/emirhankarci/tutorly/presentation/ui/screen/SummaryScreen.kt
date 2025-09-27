package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emirhankarci.tutorly.presentation.viewmodel.SummaryViewModel
import com.emirhankarci.tutorly.domain.entity.GradeSubjectKey
import com.emirhankarci.tutorly.domain.entity.chaptersByGradeAndSubject

@Composable
fun SummaryScreen(
    grade: Int,
    subject: String,
    chapter: String,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {}
) {
    val viewModel: SummaryViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(grade, subject, chapter) {
        viewModel.loadSummary(grade, subject, chapter)
    }

    val subjectColor = remember(grade, subject) {
        chaptersByGradeAndSubject[GradeSubjectKey(grade, subject)]
            ?.firstOrNull()
            ?.backgroundColor ?: Color(0xFF1976D2)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(subjectColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                when {
                    uiState.isLoading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    uiState.errorMessage != null -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = uiState.errorMessage ?: "Hata",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 16.sp
                            )
                        }
                    }

                    else -> {
                        val summary = uiState.summary
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                text = "Sınıf: $grade",
                                fontSize = 14.sp,
                                color = Color(0xFF6B7280),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "$subject - $chapter",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF1976D2),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp)
                            )

                            summary?.title?.takeIf { it.isNotBlank() }?.let { t ->
                                Text(
                                    text = t,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )
                            }

                            HorizontalDivider(
                                color = Color(0xFFE5E7EB),
                                thickness = 1.dp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Text(
                                text = summary?.summary ?: "",
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                color = Color(0xFF374151),
                                textAlign = TextAlign.Justify
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryScreenPreview() {
    MaterialTheme {
        SummaryScreen(
            grade = 10,
            subject = "Matematik",
            chapter = "Fonksiyonlar"
        )
    }
}