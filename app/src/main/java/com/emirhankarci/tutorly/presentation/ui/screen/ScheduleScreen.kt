package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhankarci.tutorly.domain.entity.ScheduleData
import com.emirhankarci.tutorly.domain.entity.ScheduleItem

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    onAddLesson: () -> Unit = {},
    onEditLesson: (ScheduleItem) -> Unit = {},
    onNavigateToProgress: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Simple Header with buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ders Programı",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Progress Button
                OutlinedButton(
                    onClick = onNavigateToProgress,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Progress",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("İlerleme")
                }

                // Add Lesson Button
                Button(
                    onClick = onAddLesson,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Ekle")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Simple lessons list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(ScheduleData.sampleSchedule) { lesson ->
                BasicLessonCard(
                    lesson = lesson,
                    onEditLesson = { onEditLesson(lesson) }
                )
            }
        }
    }
}

@Composable
fun BasicLessonCard(
    lesson: ScheduleItem,
    onEditLesson: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEditLesson() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Simple color indicator
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        color = lesson.color,
                        shape = RoundedCornerShape(6.dp)
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Lesson info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = lesson.subject,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = "${lesson.day} - ${lesson.time}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Simple edit/delete buttons
            Row {
                IconButton(
                    onClick = onEditLesson,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = { /* TODO: Delete lesson */ },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}