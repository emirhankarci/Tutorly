package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StudyMethodScreen(
    modifier: Modifier = Modifier,
    grade: Int? = null,
    subject: String? = null,
    chapter: String? = null,
    onAIChatClick: () -> Unit = {},
    onSummaryClick: () -> Unit = {},
    onQuizClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Context header if grade, subject, and chapter are provided
        if (grade != null && subject != null && chapter != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Çalışma Konun",
                        fontSize = 14.sp,
                        color = Color(0xFF6B7280),
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${grade}. Sınıf $subject",
                        fontSize = 16.sp,
                        color = Color(0xFF1976D2),
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = chapter,
                        fontSize = 18.sp,
                        color = Color(0xFF3C0A8D),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        // Study method buttons
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            StudyMethodButton(
                title = "AI Chat",
                subtitle = "Yapay zeka ile sohbet et ve sorular sor",
                icon = Icons.Default.Home,
                backgroundColor = Color(0xFF2196F3),
                onClick = onAIChatClick,
                modifier = Modifier.weight(1f)
            )

            StudyMethodButton(
                title = "Konu Özeti",
                subtitle = "Seçili konunun özetini gör",
                icon = Icons.Default.Home,
                backgroundColor = Color(0xFF4CAF50),
                onClick = onSummaryClick,
                modifier = Modifier.weight(1f)
            )

            StudyMethodButton(
                title = "Quiz Oluştur",
                subtitle = "Konuyla ilgili quiz çöz",
                icon = Icons.Default.Home,
                backgroundColor = Color(0xFFFF9800),
                onClick = onQuizClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StudyMethodButton(
    title: String,
    subtitle: String,
    icon: ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudyMethodScreenPreview() {
    MaterialTheme {
        StudyMethodScreen()
    }
}