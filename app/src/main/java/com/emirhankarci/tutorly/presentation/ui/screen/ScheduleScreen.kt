package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhankarci.tutorly.domain.entity.ScheduleData
import com.emirhankarci.tutorly.domain.entity.ScheduleItem

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    onAddLesson: () -> Unit = {},
    onEditLesson: (ScheduleItem) -> Unit = {}
) {
    var selectedDay by remember { mutableStateOf("Pazartesi") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header Section
        item {
            ScheduleHeader(onAddLesson = onAddLesson)
        }

        // Week Days Selector
        item {
            WeekDaysSelector(
                selectedDay = selectedDay,
                onDaySelected = { selectedDay = it }
            )
        }

        // Today's Schedule
        item {
            TodaysSchedule(
                selectedDay = selectedDay,
                onEditLesson = onEditLesson
            )
        }

        // Weekly Overview
        item {
            WeeklyOverview()
        }
    }
}

@Composable
fun ScheduleHeader(onAddLesson: () -> Unit = {}) {
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
                Column {
                    Text(
                        text = "Ders Programın",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Bu hafta ${ScheduleData.sampleSchedule.size} ders var",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FloatingActionButton(
                        onClick = onAddLesson,
                        containerColor = Color.White,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Lesson",
                            tint = Color(0xFF667eea)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeekDaysSelector(
    selectedDay: String,
    onDaySelected: (String) -> Unit
) {
    Column {
        Text(
            text = "Haftalık Görünüm",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(ScheduleData.weekDays) { day ->
                DayCard(
                    day = day,
                    isSelected = day == selectedDay,
                    onClick = { onDaySelected(day) },
                    hasLessons = ScheduleData.sampleSchedule.any { it.day == day }
                )
            }
        }
    }
}

@Composable
fun DayCard(
    day: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    hasLessons: Boolean
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(100.dp)
            .height(80.dp)
            .shadow(if (isSelected) 12.dp else 4.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF667eea) else Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = day.take(3),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else Color(0xFF2D3748),
                textAlign = TextAlign.Center
            )

            if (hasLessons) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = if (isSelected) Color.White else Color(0xFF667eea),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    }
}

@Composable
fun TodaysSchedule(
    selectedDay: String,
    onEditLesson: (ScheduleItem) -> Unit = {}
) {
    val dayLessons = ScheduleData.sampleSchedule.filter { it.day == selectedDay }

    Column {
        Text(
            text = "$selectedDay Programı",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (dayLessons.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Bu gün ders yok 🎉",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dayLessons.forEach { lesson ->
                    LessonCard(
                        lesson = lesson,
                        onEditLesson = { onEditLesson(lesson) }
                    )
                }
            }
        }
    }
}

@Composable
fun LessonCard(
    lesson: ScheduleItem,
    onEditLesson: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color indicator
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                lesson.color,
                                lesson.color.copy(alpha = 0.7f)
                            )
                        ),
                        shape = RoundedCornerShape(25.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Time",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Lesson details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = lesson.subject,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D3748)
                )
                Text(
                    text = "${lesson.time} • ${lesson.duration}",
                    fontSize = 14.sp,
                    color = Color(0xFF718096)
                )
            }

            // Edit button
            IconButton(
                onClick = onEditLesson,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit lesson",
                    tint = Color(0xFF667eea),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun WeeklyOverview() {
    Column {
        Text(
            text = "Bu Hafta",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OverviewCard(
                title = "Toplam Ders",
                value = "${ScheduleData.sampleSchedule.size}",
                color = Color(0xFF48bb78),
                modifier = Modifier.weight(1f)
            )

            OverviewCard(
                title = "Bu Hafta",
                value = "${ScheduleData.getTotalWeeklyDuration()} dk",
                color = Color(0xFFed8936),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun OverviewCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(80.dp)
            .shadow(6.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            color,
                            color.copy(alpha = 0.8f)
                        )
                    )
                )
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleScreenPreview() {
    MaterialTheme {
        ScheduleScreen()
    }
}