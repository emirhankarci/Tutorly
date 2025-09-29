package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider as Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.emirhankarci.tutorly.domain.entity.ScheduleData
import com.emirhankarci.tutorly.domain.entity.ScheduleItem
import com.emirhankarci.tutorly.presentation.viewmodel.ScheduleViewModel

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    onAddLesson: (String?, String?) -> Unit = { _, _ -> },
    onEditLesson: (ScheduleItem) -> Unit = {},
    onNavigateToProgress: () -> Unit = {},
    onNavigateToLessonPlanChat: () -> Unit = {},
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val lessons = uiState.lessons
    var selectedLesson by remember { mutableStateOf<ScheduleItem?>(null) }
    var showLessonActionDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var showClearAllConfirmation by remember { mutableStateOf(false) }

    val days = listOf("Çalışma Saatleri", "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar")
    val dayNames = listOf("", "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar")

    // Function to get lesson for a specific time slot and day
    fun getLessonForTimeSlot(rowIndex: Int, columnIndex: Int): ScheduleItem? {
        if (columnIndex == 0) return null // Time column
        val dayName = dayNames[columnIndex]
        val currentHour = 8 + rowIndex

        return lessons.find { lesson ->
            if (lesson.day != dayName) return@find false

            // Parse lesson start time
            val lessonStartHour = try {
                lesson.time.substring(0, 2).toInt()
            } catch (e: Exception) { 0 }

            // Parse lesson duration (remove "dk" suffix and convert to int)
            val lessonDurationMinutes = try {
                lesson.duration.replace(" dk", "").replace("dk", "").trim().toInt()
            } catch (e: Exception) { 60 }

            // Calculate how many hours this lesson spans
            val lessonDurationHours = (lessonDurationMinutes + 59) / 60 // Round up to next hour
            val lessonEndHour = lessonStartHour + lessonDurationHours

            // Check if current time slot falls within this lesson's time range
            currentHour >= lessonStartHour && currentHour < lessonEndHour
        }
    }

    // Function to get the remaining duration for a specific cell in a lesson
    fun getRemainingDurationForCell(rowIndex: Int, columnIndex: Int, lesson: ScheduleItem?): Int {
        if (lesson == null || columnIndex == 0) return 0

        val currentHour = 8 + rowIndex
        val lessonStartHour = try {
            lesson.time.substring(0, 2).toInt()
        } catch (e: Exception) { 0 }

        val lessonDurationMinutes = try {
            lesson.duration.replace(" dk", "").replace("dk", "").trim().toInt()
        } catch (e: Exception) { 60 }

        // Calculate how many minutes have passed since lesson start
        val minutesSinceStart = (currentHour - lessonStartHour) * 60

        // Calculate remaining duration for this cell (max 60 minutes per cell)
        val remainingTotal = lessonDurationMinutes - minutesSinceStart
        return minOf(remainingTotal, 60) // Each cell represents max 60 minutes
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Table with horizontal scroll
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                // Header row
                Row(modifier = Modifier.width(IntrinsicSize.Max)) {
                    days.forEach { day ->
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .border(1.dp, Color.Black)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                maxLines = 2
                            )
                        }
                    }
                }

                // Data rows - 10 rows for better visibility
                repeat(10) { rowIndex ->
                    Row(modifier = Modifier.width(IntrinsicSize.Max)) {
                        repeat(8) { columnIndex ->
                            val lesson = getLessonForTimeSlot(rowIndex, columnIndex)
                            val remainingDuration = getRemainingDurationForCell(rowIndex, columnIndex, lesson)
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .border(1.dp, Color.Black)
                                    .background(
                                        if (lesson != null) lesson.color.copy(alpha = 0.3f) else Color.Transparent
                                    )
                                    .clickable(enabled = columnIndex > 0) {
                                        if (columnIndex > 0) {
                                            if (lesson != null) {
                                                // Show action dialog for existing lesson
                                                selectedLesson = lesson
                                                showLessonActionDialog = true
                                            } else {
                                                // Add new lesson
                                                val dayName = dayNames[columnIndex]
                                                val timeSlot = String.format("%02d:00", 8 + rowIndex)
                                                onAddLesson(dayName, timeSlot)
                                            }
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = when {
                                        columnIndex == 0 -> "${8 + rowIndex}:00\n${9 + rowIndex}:00"
                                        lesson != null -> {
                                            buildString {
                                                append(lesson.subject)
                                                if (lesson.topic.isNotBlank()) {
                                                    append("\n${lesson.topic}")
                                                }
                                                append("\n$remainingDuration dk")
                                            }
                                        }
                                        else -> ""
                                    },
                                    fontSize = 9.sp,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 5,
                                    fontWeight = if (lesson != null) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            // Add some bottom padding to account for the floating action button
            Spacer(modifier = Modifier.height(80.dp))
        }

        // Floating Action Button - Add Lesson Plan
        FloatingActionButton(
            onClick = { onNavigateToLessonPlanChat() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Create Lesson Plan with AI"
            )
        }

        // Floating Action Button - Clear All Lessons
        FloatingActionButton(
            onClick = { showClearAllConfirmation = true },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Clear All Lessons"
            )
        }

        // Lesson Action Dialog
        if (showLessonActionDialog && selectedLesson != null) {
            AlertDialog(
                onDismissRequest = {
                    showLessonActionDialog = false
                    selectedLesson = null
                },
                title = {
                    Text("Ders Seçenekleri")
                },
                text = {
                    Text("${selectedLesson!!.subject} dersi için ne yapmak istiyorsunuz?")
                },
                confirmButton = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextButton(
                            onClick = {
                                selectedLesson?.let { lesson ->
                                    onEditLesson(lesson)
                                }
                                showLessonActionDialog = false
                                selectedLesson = null
                            }
                        ) {
                            Text("Düzenle")
                        }

                        TextButton(
                            onClick = {
                                showDeleteConfirmation = true
                                showLessonActionDialog = false
                            }
                        ) {
                            Text("Sil")
                        }
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showLessonActionDialog = false
                            selectedLesson = null
                        }
                    ) {
                        Text("İptal")
                    }
                }
            )
        }

        // Delete Confirmation Dialog
        if (showDeleteConfirmation && selectedLesson != null) {
            AlertDialog(
                onDismissRequest = {
                    showDeleteConfirmation = false
                    selectedLesson = null
                },
                title = {
                    Text("Dersi Sil")
                },
                text = {
                    Text("${selectedLesson!!.subject} dersini silmek istediğinize emin misiniz?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedLesson?.let { lesson ->
                                viewModel.removeLesson(lesson)
                            }
                            showDeleteConfirmation = false
                            selectedLesson = null
                        }
                    ) {
                        Text("Sil")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteConfirmation = false
                            selectedLesson = null
                        }
                    ) {
                        Text("İptal")
                    }
                }
            )
        }

        // Clear All Confirmation Dialog
        if (showClearAllConfirmation) {
            AlertDialog(
                onDismissRequest = {
                    showClearAllConfirmation = false
                },
                title = {
                    Text("Tüm Dersleri Temizle")
                },
                text = {
                    Text("Tablodaki tüm dersleri silmek istediğinize emin misiniz? Bu işlem geri alınamaz.")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.clearAllLessons()
                            showClearAllConfirmation = false
                        }
                    ) {
                        Text("Sil", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showClearAllConfirmation = false
                        }
                    ) {
                        Text("İptal")
                    }
                }
            )
        }

    }
}

@Composable
private fun SimpleScheduleCard(
    lesson: ScheduleItem
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Subject Icon with lesson color
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        lesson.color,
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Subject Info
            Column {
                Text(
                    text = lesson.subject,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (lesson.notes.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = lesson.notes,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                    )
                }
                Text(
                    text = "${lesson.day} • ${lesson.time} • ${lesson.duration}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

            }
        }
    }
}