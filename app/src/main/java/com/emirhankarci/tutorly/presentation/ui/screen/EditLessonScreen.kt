package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhankarci.tutorly.domain.entity.ScheduleData
import com.emirhankarci.tutorly.domain.entity.ScheduleItem

@Composable
fun EditLessonScreen(
    modifier: Modifier = Modifier,
    lesson: ScheduleItem? = null,
    onSaveLesson: (ScheduleItem) -> Unit = {},
    onDeleteLesson: (ScheduleItem) -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    var subject by remember { mutableStateOf(lesson?.subject ?: "") }
    var selectedDay by remember { mutableStateOf(lesson?.day ?: "Pazartesi") }
    var time by remember { mutableStateOf(lesson?.time ?: "") }
    var duration by remember { mutableStateOf(lesson?.duration?.replace(" dk", "") ?: "") }
    var selectedColor by remember { mutableStateOf(lesson?.color ?: Color(0xFF2196F3)) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val subjectColors = listOf(
        Color(0xFF2196F3), // Blue
        Color(0xFFF44336), // Red
        Color(0xFF4CAF50), // Green
        Color(0xFF9C27B0), // Purple
        Color(0xFFFF9800), // Orange
        Color(0xFF607D8B), // Blue Grey
        Color(0xFF795548), // Brown
        Color(0xFFE91E63)  // Pink
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header
        item {
            EditLessonHeader(
                onBackPressed = onBackPressed,
                onDeleteClicked = { showDeleteDialog = true }
            )
        }

        // Subject Input
        item {
            SubjectInputSection(
                subject = subject,
                onSubjectChange = { subject = it }
            )
        }

        // Day Selection
        item {
            DaySelectionSection(
                selectedDay = selectedDay,
                onDaySelected = { selectedDay = it }
            )
        }

        // Time Input
        item {
            TimeInputSection(
                time = time,
                onTimeChange = { time = it },
                duration = duration,
                onDurationChange = { duration = it }
            )
        }

        // Color Selection
        item {
            ColorSelectionSection(
                selectedColor = selectedColor,
                colors = subjectColors,
                onColorSelected = { selectedColor = it }
            )
        }

        // Save Button
        item {
            EditSaveButton(
                enabled = subject.isNotBlank() && time.isNotBlank() && duration.isNotBlank(),
                onSave = {
                    val updatedLesson = ScheduleItem(
                        subject = subject,
                        time = time,
                        duration = "$duration dk",
                        color = selectedColor,
                        day = selectedDay
                    )
                    onSaveLesson(updatedLesson)
                }
            )
        }
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "Dersi Sil",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("Bu dersi silmek istediğinizden emin misiniz? Bu işlem geri alınamaz.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        lesson?.let { onDeleteLesson(it) }
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336)
                    )
                ) {
                    Text("Sil", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("İptal")
                }
            }
        )
    }
}

@Composable
fun EditLessonHeader(
    onBackPressed: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF667eea),
                            Color(0xFF764ba2)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ders Düzenle",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onDeleteClicked,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete lesson",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun EditSaveButton(
    enabled: Boolean,
    onSave: () -> Unit
) {
    Button(
        onClick = onSave,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF667eea),
            disabledContainerColor = Color.Gray
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Save",
                tint = Color.White
            )
            Text(
                text = "Değişiklikleri Kaydet",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditLessonScreenPreview() {
    val sampleLesson = ScheduleItem(
        subject = "Matematik",
        time = "09:00",
        duration = "90 dk",
        color = Color(0xFF2196F3),
        day = "Pazartesi"
    )

    MaterialTheme {
        EditLessonScreen(lesson = sampleLesson)
    }
}