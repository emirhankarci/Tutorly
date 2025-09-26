package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhankarci.tutorly.domain.entity.UserProgress

@Composable
fun ProgressScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {}
) {
    // Sample progress data - in real app this would come from ViewModel/Repository
    val sampleProgress = UserProgress(
        totalStudyTime = 1250L, // 20 hours 50 minutes
        completedLessons = 15,
        weeklyGoal = 300,
        currentStreak = 5,
        bestStreak = 12,
        lastStudyDate = System.currentTimeMillis(),
        subjectsStudied = mapOf(
            "Matematik" to 400,
            "Fizik" to 350,
            "Kimya" to 300,
            "Türkçe" to 200
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Text(
                text = "İlerleme Raporu",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // Stats Cards
        item {
            StatsGrid(progress = sampleProgress)
        }

        // Weekly Goal Progress
        item {
            WeeklyGoalCard(progress = sampleProgress)
        }

        // Subject Breakdown
        item {
            SubjectBreakdownCard(progress = sampleProgress)
        }
    }
}

@Composable
fun StatsGrid(progress: UserProgress) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "Toplam Süre",
                value = "${progress.totalStudyTime / 60}s ${progress.totalStudyTime % 60}dk",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Tamamlanan Ders",
                value = "${progress.completedLessons}",
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "Güncel Seri",
                value = "${progress.currentStreak} gün",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "En İyi Seri",
                value = "${progress.bestStreak} gün",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = title,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun WeeklyGoalCard(progress: UserProgress) {
    val weeklyProgress = (progress.totalStudyTime % (7 * 24 * 60)).toFloat() // This week's progress
    val progressPercentage = (weeklyProgress / progress.weeklyGoal).coerceAtMost(1f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Haftalık Hedef",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Progress",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { progressPercentage },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.LightGray,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${weeklyProgress.toInt()} / ${progress.weeklyGoal} dakika",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun SubjectBreakdownCard(progress: UserProgress) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Ders Bazında İlerleme",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            progress.subjectsStudied.entries.sortedByDescending { it.value }.forEach { (subject, minutes) ->
                SubjectProgressRow(
                    subject = subject,
                    minutes = minutes,
                    totalMinutes = progress.totalStudyTime.toInt()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun SubjectProgressRow(
    subject: String,
    minutes: Int,
    totalMinutes: Int
) {
    val percentage = if (totalMinutes > 0) (minutes.toFloat() / totalMinutes) else 0f

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = subject,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "${minutes} dk",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        LinearProgressIndicator(
            progress = { percentage },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            trackColor = Color.LightGray.copy(alpha = 0.3f),
        )
    }
}