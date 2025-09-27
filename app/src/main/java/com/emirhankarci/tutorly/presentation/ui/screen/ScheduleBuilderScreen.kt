package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emirhankarci.tutorly.presentation.ui.screen.TutorlyCard
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.times
import com.emirhankarci.tutorly.domain.entity.SubjectInfo
import com.emirhankarci.tutorly.domain.entity.subjects

@Composable
fun ScheduleBuilderScreen(
    modifier: Modifier = Modifier,
    onSaved: () -> Unit = {},
    viewModel: ScheduleBuilderViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val currentDay = uiState.days[uiState.currentDayIndex]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Text(
            text = "Gün",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Days grid (4 columns first row, remaining below) dynamic height like plan
        val daysColumns = 4
        val dayRows = (uiState.days.size + daysColumns - 1) / daysColumns
        val dayRowHeight = 60.dp
        val dayVSpacing = 8.dp
        val daysTotalHeight = dayRows * dayRowHeight + (dayRows - 1).coerceAtLeast(0) * dayVSpacing
        LazyVerticalGrid(
            columns = GridCells.Fixed(daysColumns),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(dayVSpacing),
            modifier = Modifier.height(daysTotalHeight)
        ) {
            items(uiState.days) { day ->
                val index = uiState.days.indexOf(day)
                val isSelected = day == currentDay
                val dayColors = listOf(
                    Color(0xFF1976D2), // Pazartesi
                    Color(0xFFE91E63), // Salı
                    Color(0xFFFF5722), // Çarşamba
                    Color(0xFF4CAF50), // Perşembe
                    Color(0xFF9C27B0), // Cuma
                    Color(0xFFFF9800), // Cumartesi
                    Color(0xFF795548)  // Pazar
                )
                val borderColor = dayColors.getOrElse(index) { MaterialTheme.colorScheme.primary }
                SmallTutorlyCard(
                    title = day,
                    icon = Icons.Default.DateRange,
                    selected = isSelected,
                    borderColor = borderColor,
                    width = 80.dp,
                    height = 60.dp,
                    fontSize = 12.sp,
                    onClick = { viewModel.setCurrentDay(index) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hours grid
        Text(
            text = "$currentDay için saat seç",
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))

        var selectedHour by remember(currentDay) { mutableStateOf<String?>(null) }
        var subjectPickerOpen by remember(currentDay) { mutableStateOf(false) }

        // Hours grid dynamic height
        val hoursColumns = 6
        val hourRows = (uiState.hours.size + hoursColumns - 1) / hoursColumns
        val hourRowHeight = 32.dp
        val hourVSpacing = 4.dp
        val hoursTotalHeight = hourRows * hourRowHeight + (hourRows - 1).coerceAtLeast(0) * hourVSpacing
        LazyVerticalGrid(
            columns = GridCells.Fixed(hoursColumns),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(hourVSpacing),
            modifier = Modifier.height(hoursTotalHeight)
        ) {
            items(uiState.hours) { hour ->
                val active = selectedHour == hour
                SmallTutorlyCard(
                    title = hour,
                    icon = null,
                    selected = active,
                    borderColor = if (active) MaterialTheme.colorScheme.primary else Color(0xFFB0BEC5),
                    width = 56.dp,
                    height = 32.dp,
                    fontSize = 10.sp,
                    onClick = {
                        selectedHour = hour
                        subjectPickerOpen = true
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Subject picker
        if (subjectPickerOpen && selectedHour != null) {
            Text(
                text = "Ders seç",
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Subjects grid dynamic height
            val subjectsList = uiState.subjects.distinct()
            val subjectsColumns = 3
            val subjRows = (subjectsList.size + subjectsColumns - 1) / subjectsColumns
            val subjRowHeight = 112.dp
            val subjVSpacing = 8.dp
            val subjectsTotalHeight = subjRows * subjRowHeight + (subjRows - 1).coerceAtLeast(0) * subjVSpacing
            LazyVerticalGrid(
                columns = GridCells.Fixed(subjectsColumns),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(subjVSpacing),
                modifier = Modifier.height(subjectsTotalHeight).padding(start = 12.dp, end = 12.dp)
            ) {
                items(subjectsList) { subject ->
                    val info = resolveSubjectInfo(subject)
                    if (info != null) {
                        MiniSubjectCard(
                            info = info,
                            onClick = {
                                viewModel.addLesson(currentDay, selectedHour!!, subject)
                                subjectPickerOpen = false
                                selectedHour = null
                            }
                        )
                    } else {
                        SmallTutorlyCard(
                            title = subject,
                            icon = Icons.Default.Home,
                            selected = false,
                            borderColor = MaterialTheme.colorScheme.primary,
                            width = 140.dp,
                            height =140.dp,
                            fontSize = 16.sp,
                            onClick = {
                                viewModel.addLesson(currentDay, selectedHour!!, subject)
                                subjectPickerOpen = false
                                selectedHour = null
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Summary for the day
        Text(
            text = "$currentDay planı",
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        val dayItems = uiState.selections[currentDay].orEmpty()
        if (dayItems.isEmpty()) {
            Text(text = "Henüz ders eklenmedi", color = Color.Black.copy(alpha = 0.7f))
        } else {
            val rows = (dayItems.size + 1) / 2
            val rowHeight = 110.dp
            val spacing = 8.dp
            val totalHeight = rows * rowHeight + (rows - 1).coerceAtLeast(0) * spacing
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(totalHeight)
            ) {
                items(dayItems) { item ->
                    val info = resolveSubjectInfo(item.subject)
                    SummaryTutorlyMini(
                        title = item.subject,
                        time = item.time,
                        borderColor = info?.borderColor ?: MaterialTheme.colorScheme.primary,
                        textColor = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = { viewModel.previousDay() },
                enabled = uiState.currentDayIndex > 0
            ) { Text("Geri") }

            if (uiState.currentDayIndex < uiState.days.lastIndex) {
                Button(onClick = { viewModel.nextDay() }) { Text("İleri") }
            } else {
                Button(
                    onClick = { viewModel.saveAll(onDone = onSaved) },
                    enabled = !uiState.isSaving
                ) {
                    Text(if (uiState.isSaving) "Kaydediliyor..." else "Kaydet")
                }
            }
        }

        uiState.errorMessage?.let { err ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = err, color = Color(0xFFFFCDD2))
        }
    }
}

@Composable
private fun SmallTutorlyCard(
    title: String,
    icon: ImageVector? = null,
    selected: Boolean,
    borderColor: Color,
    width: Dp,
    height: Dp,
    fontSize: TextUnit,
    onClick: () -> Unit
) {
    val effectiveBorder = if (selected) borderColor else borderColor.copy(alpha = 0.7f)
    val textColor = Color.Black
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .padding(0.dp)
                .width(width)
                .height(height)
                .border(2.dp, effectiveBorder, RoundedCornerShape(10.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            icon?.let { Icon(it, contentDescription = null, tint = effectiveBorder) }
            if (icon != null) Spacer(Modifier.height(2.dp))
            Text(
                text = title,
                fontSize = fontSize,
                color = textColor,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

@Composable
private fun MiniSubjectCard(
    info: SubjectInfo,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .width(112.dp)
                .height(112.dp)
                .padding(6.dp)
                .border(2.dp, info.borderColor.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(info.backgroundColor, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = info.icon),
                    contentDescription = info.title,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(Modifier.height(6.dp))
            Text(
                text = info.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = info.backgroundColor,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

private fun resolveSubjectInfo(title: String): SubjectInfo? {
    val normalized = title.lowercase()
    val direct = subjects.firstOrNull { it.title.equals(title, ignoreCase = true) }
    if (direct != null) return direct
    val alias = when {
        normalized == "türkçe" || normalized.contains("edebiyat") ->
            subjects.firstOrNull { it.title.contains("Edebiyat", ignoreCase = true) }
        else -> null
    }
    return alias
}

@Composable
private fun SummaryTutorlyMini(
    title: String,
    time: String,
    borderColor: Color,
    textColor: Color
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, borderColor.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontWeight = FontWeight.Bold, color = textColor, textAlign = TextAlign.Center)
            Spacer(Modifier.height(4.dp))
            Text(time, color = textColor.copy(alpha = 0.7f), fontSize = 12.sp, textAlign = TextAlign.Center)
        }
    }
}


