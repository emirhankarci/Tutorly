package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import com.emirhankarci.tutorly.R
import com.emirhankarci.tutorly.domain.entity.ScheduleData
import com.emirhankarci.tutorly.domain.entity.ScheduleItem

@Composable
fun NotesInputSection(
    notes: String,
    onNotesChange: (String) -> Unit
) {
    Column {
        Text(
            text = "Not / Konu",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF667eea),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = notes,
            onValueChange = onNotesChange,
            placeholder = { Text("Fonksiyonlar, dil bilgisi, vektörler...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFF667eea),
                unfocusedBorderColor = Color.Gray
            )
        )
    }
}

@Composable
fun AddLessonScreen(
    modifier: Modifier = Modifier,
    onSaveLesson: (ScheduleItem) -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    var subject by remember { mutableStateOf("") }
    var selectedDay by remember { mutableStateOf("Pazartesi") }
    var time by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Color(0xFF2196F3)) }
    var notes by remember { mutableStateOf("") }

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
        // Header removed; top bar now shows title

        // Subject Input
        item {
            SubjectInputSection(
                subject = subject,
                onSubjectChange = { subject = it }
            )
        }
        // Notes Input
        item {
            NotesInputSection(
                notes = notes,
                onNotesChange = { notes = it }
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
            SaveButton(
                enabled = subject.isNotBlank() && time.isNotBlank() && duration.isNotBlank(),
                onSave = {
                    val newLesson = ScheduleItem(
                        subject = subject,
                        time = time,
                        duration = "$duration dk",
                        color = selectedColor,
                        day = selectedDay,
                        notes = notes
                    )
                    onSaveLesson(newLesson)
                }
            )
        }
    }
}

@Composable
fun AddLessonHeader(onBackPressed: () -> Unit) {
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
            Text(
                text = "Yeni Ders Ekle",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun SubjectInputSection(
    subject: String,
    onSubjectChange: (String) -> Unit
) {
    Column {
        Text(
            text = "Ders Adı",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color =  Color(0xFF667eea),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = subject,
            onValueChange = onSubjectChange,
            placeholder = { Text("Matematik, Fizik, Kimya...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFF667eea),
                unfocusedBorderColor = Color.Gray
            )
        )
    }
}

@Composable
fun DaySelectionSection(
    selectedDay: String,
    onDaySelected: (String) -> Unit
) {
    Column {
        Text(
            text = "Gün Seçimi",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color =Color(0xFF667eea),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(ScheduleData.weekDays) { day ->
                DaySelectionCard(
                    day = day,
                    isSelected = day == selectedDay,
                    onClick = { onDaySelected(day) }
                )
            }
        }
    }
}

@Composable
fun DaySelectionCard(
    day: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(80.dp)
            .height(60.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF667eea) else Color.White
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.take(3),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else Color(0xFF2D3748),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TimeInputSection(
    time: String,
    onTimeChange: (String) -> Unit,
    duration: String,
    onDurationChange: (String) -> Unit
) {
    Column {
        Text(
            text = "Saat ve Süre",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color =Color(0xFF667eea),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TimePickerField(
                time = time,
                onTimeChange = onTimeChange,
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = duration,
                onValueChange = onDurationChange,
                placeholder = { Text("90") },
                label = { Text("Süre (dk)") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF667eea),
                    unfocusedBorderColor = Color.Gray
                )
            )
        }
    }
}

@Composable
fun ColorSelectionSection(
    selectedColor: Color,
    colors: List<Color>,
    onColorSelected: (Color) -> Unit
) {
    Column {
        Text(
            text = "Renk Seçimi",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color =Color(0xFF667eea),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(colors) { color ->
                ColorSelectionItem(
                    color = color,
                    isSelected = color == selectedColor,
                    onClick = { onColorSelected(color) }
                )
            }
        }
    }
}

@Composable
fun ColorSelectionItem(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .size(50.dp)
            .shadow(if (isSelected) 8.dp else 4.dp, RoundedCornerShape(25.dp)),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun SaveButton(
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
                imageVector = Icons.Default.DateRange,
                contentDescription = "Save",
                tint = Color.White
            )
            Text(
                text = "Dersi Kaydet",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerField(
    time: String,
    onTimeChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    OutlinedTextField(
        value = time,
        onValueChange = { }, // Read-only
        placeholder = { Text("09:00") },
        label = { Text("Saat") },
        modifier = modifier,
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showTimePicker = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.timer),
                    contentDescription = "Select Time",
                    tint = Color(0xFF667eea)
                )
            }
        }
        ,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color(0xFF667eea),
            unfocusedBorderColor = Color.Gray
        )
    )

    if (showTimePicker) {
        TimePickerDialog(
            onTimeSelected = { hour, minute ->
                val formattedTime = String.format("%02d:%02d", hour, minute)
                onTimeChange(formattedTime)
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false },
            timePickerState = timePickerState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDialog(
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit,
    timePickerState: TimePickerState
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Time") },
        text = {
            TimePicker(state = timePickerState)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(timePickerState.hour, timePickerState.minute)
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddLessonScreenPreview() {
    MaterialTheme {
        AddLessonScreen()
    }
}