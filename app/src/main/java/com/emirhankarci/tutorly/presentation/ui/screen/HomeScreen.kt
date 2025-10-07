package com.emirhankarci.tutorly.presentation.ui.screen

import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhankarci.tutorly.R
import com.emirhankarci.tutorly.domain.entity.TutorlyCardItem
import com.emirhankarci.tutorly.domain.entity.TutorlyCardItemResult
import com.emirhankarci.tutorly.domain.entity.ScheduleData
import com.emirhankarci.tutorly.presentation.viewmodel.UserProfileViewModel
import com.emirhankarci.tutorly.presentation.viewmodel.ScheduleViewModel
import com.emirhankarci.tutorly.domain.entity.ScheduleItem
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToGradeSelection: () -> Unit = {},
    onNavigateToSchedule: () -> Unit = {},
    onNavigateToEnglishLearning: () -> Unit = {},
    onNavigateToStudyWithImage: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    scheduleViewModel: ScheduleViewModel? = null
) {
    HomeScreenContent(
        modifier = modifier,
        onNavigateToGradeSelection = onNavigateToGradeSelection,
        onNavigateToSchedule = onNavigateToSchedule,
        onNavigateToEnglishLearning = onNavigateToEnglishLearning,
        onNavigateToStudyWithImage = onNavigateToStudyWithImage,
        onNavigateToSettings = onNavigateToSettings,
        scheduleViewModel = scheduleViewModel ?: hiltViewModel()
    )
}


@Composable
fun HomeScreenContent(
    modifier: Modifier,
    onNavigateToGradeSelection: () -> Unit = {},
    onNavigateToSchedule: () -> Unit = {},
    onNavigateToEnglishLearning: () -> Unit = {},
    onNavigateToStudyWithImage: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    userProfileViewModel: UserProfileViewModel = hiltViewModel(),
    scheduleViewModel: ScheduleViewModel = hiltViewModel()
) {
    val userProfileState by userProfileViewModel.uiState.collectAsState()
    val scheduleState by scheduleViewModel.uiState.collectAsState()

    // Find next upcoming lesson
    val nextLesson = findNextUpcomingLesson(scheduleState.lessons)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        // Greeting section
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF8F9FA)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Time-based icon
                    userProfileState.greetingData?.let { greetingData ->
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    color = greetingData.iconColor.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(24.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = greetingData.iconRes),
                                contentDescription = "Time icon",
                                tint = greetingData.iconColor,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    } ?: Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color(0xFF667eea),
                                shape = RoundedCornerShape(24.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = "Profil",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = userProfileState.greetingData?.text ?: userProfileState.greeting,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF2D3748)
                        )
                        Text(
                            text = "Yeni bir şey öğrenmeye hazır mısın?",
                            fontSize = 14.sp,
                            color = Color(0xFF6B7280),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }

        item {
            TutorlyCard(
                borderColor = Color(0xFF667eea),
                textColor = Color.White,
                icon = R.drawable.homescreen_ai_1,
                title = "AI Sohbet",
                description = "İngilizce öğrenmek için AI ile konuş",
                cardHeight = 150.dp,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onNavigateToEnglishLearning() },
                isMainCard = true
            )
        }

        item { Spacer(Modifier.height(8.dp)) }

        item {
            Text(
                "Hızlı Eylemler",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.Start,
                color = Color.Black
            )
        }

        item { Spacer(Modifier.height(4.dp)) }

        items(TutorlyCardItemResult.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEachIndexed { index, item ->
                    val cardIndex = TutorlyCardItemResult.indexOf(item)
                    val borderColor = when (cardIndex) {
                        0 -> Color(0xFF2196F3) // Blue
                        1 -> Color(0xFFF44336) // Red
                        2 -> Color(0xFF4CAF50) // Green
                        3 -> Color(0xFF9C27B0) // Purple
                        else -> Color(0xFF4C9AFF) // Default blue
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        TutorlyCard(
                            borderColor,
                            Color(0xFF2D3748),
                            item.icon,
                            item.title,
                            item.description,
                            null,
                            130.dp,
                            Modifier.fillMaxWidth(),
                            when (item.title) {
                                "Konulara Git" -> { { onNavigateToGradeSelection() } }
                                "Görsel ile Çalış" -> { { onNavigateToStudyWithImage() } }
                                "Çalışma Planı" -> { { onNavigateToSchedule() } }
                                "Profil" -> { { onNavigateToSettings() } }
                                else -> { {} }
                            }
                        )
                    }

                }

                repeat(2 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        item { Spacer(Modifier.height(8.dp)) }


        item {
            Row(Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    "Sırada Ne Var",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Start,
                    color = Color.Black
                )
                Text(
                    "Tümünü Gör",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    modifier = Modifier.clickable { onNavigateToSchedule() }
                )
            }

        }

        item { Spacer(Modifier.height(8.dp)) }

        item {
            if (nextLesson != null) {
                TutorlyCard(
                    borderColor = nextLesson.color,
                    textColor = Color.White,
                    icon = Icons.Default.DateRange,
                    title = nextLesson.subject,
                    description = "${nextLesson.day} • ${nextLesson.time} • ${nextLesson.duration}",
                    cardHeight = 150.dp,
                    modifier = Modifier.fillMaxWidth(),
                    isMainCard = true,
                    onClick = { onNavigateToSchedule() }
                )
            } else {
                TutorlyCard(
                    borderColor = Color(0xFFed8936),
                    textColor = Color.White,
                    icon = Icons.Default.DateRange,
                    title = "Bugün Ders Yok",
                    description = "Her şey yolunda! 🎉",
                    cardHeight = 150.dp,
                    modifier = Modifier.fillMaxWidth(),
                    isMainCard = true,
                    onClick = { onNavigateToSchedule() }
                )
            }
        }
    }
}



@Composable
fun TutorlyCard(
    borderColor: Color,
    textColor: Color,
    icon: ImageVector,
    title: String,
    description: String? = "",
    cardWidth: Dp? = null,  // opsiyonel
    cardHeight: Dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isMainCard: Boolean = false
) {
    Card(
        onClick = onClick,
        modifier = if (cardWidth != null) {
            modifier
                .width(cardWidth)
                .height(cardHeight)
        } else {
            modifier
                .fillMaxWidth()
                .height(cardHeight)
        },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = if (isMainCard) {
                        Brush.linearGradient(
                            colors = listOf(
                                borderColor,
                                borderColor.copy(alpha = 0.8f)
                            )
                        )
                    } else {
                        Brush.linearGradient(
                            colors = listOf(
                                Color.White,
                                Color.White
                            )
                        )
                    }
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "cardicon",
                    tint = if (isMainCard) textColor else borderColor,
                    modifier = Modifier.size(if (isMainCard) 48.dp else 40.dp)
                )
                Text(
                    title,
                    fontSize = if (isMainCard) 18.sp else 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textAlign = TextAlign.Center
                )
                if (!description.isNullOrEmpty()) {
                    Text(
                        description.toString(),
                        fontSize = if (isMainCard) 14.sp else 11.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (isMainCard) textColor.copy(alpha = 0.9f) else textColor.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun TutorlyCard(
    borderColor: Color,
    textColor: Color,
    icon: Int,
    title: String,
    description: String? = "",
    cardWidth: Dp? = null,  // opsiyonel
    cardHeight: Dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isMainCard: Boolean = false
) {
    Card(
        onClick = onClick,
        modifier = if (cardWidth != null) {
            modifier
                .width(cardWidth)
                .height(cardHeight)
        } else {
            modifier
                .fillMaxWidth()
                .height(cardHeight)
        },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = if (isMainCard) {
                        Brush.linearGradient(
                            colors = listOf(
                                borderColor,
                                borderColor.copy(alpha = 0.8f)
                            )
                        )
                    } else {
                        Brush.linearGradient(
                            colors = listOf(
                                Color.White,
                                Color.White
                            )
                        )
                    }
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "cardicon",
                    tint = if (isMainCard) textColor else borderColor,
                    modifier = Modifier.size(if (isMainCard) 48.dp else 40.dp)
                )
                Text(
                    title,
                    fontSize = if (isMainCard) 18.sp else 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textAlign = TextAlign.Center
                )
                if (!description.isNullOrEmpty()) {
                    Text(
                        description.toString(),
                        fontSize = if (isMainCard) 14.sp else 11.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (isMainCard) textColor.copy(alpha = 0.9f) else textColor.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TutorlyCardPreview() {
    TutorlyCard( borderColor = Color.Blue,
        textColor = Color.Blue,
        icon = Icons.Default.ShoppingCart,
        title = "Konu",
        description = "Matematik",
        120.dp,120.dp

        )

}


// Helper function to find the next upcoming lesson
private fun findNextUpcomingLesson(lessons: List<ScheduleItem>): ScheduleItem? {
    if (lessons.isEmpty()) return null

    val calendar = Calendar.getInstance()
    val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)
    val currentTimeInMinutes = currentHour * 60 + currentMinute

    // Map day names to calendar constants
    val dayMapping = mapOf(
        "Monday" to Calendar.MONDAY,
        "Tuesday" to Calendar.TUESDAY,
        "Wednesday" to Calendar.WEDNESDAY,
        "Thursday" to Calendar.THURSDAY,
        "Friday" to Calendar.FRIDAY,
        "Saturday" to Calendar.SATURDAY,
        "Sunday" to Calendar.SUNDAY,
        "Pazartesi" to Calendar.MONDAY,
        "Salı" to Calendar.TUESDAY,
        "Çarşamba" to Calendar.WEDNESDAY,
        "Perşembe" to Calendar.THURSDAY,
        "Cuma" to Calendar.FRIDAY,
        "Cumartesi" to Calendar.SATURDAY,
        "Pazar" to Calendar.SUNDAY
    )

    // Filter and sort lessons
    val upcomingLessons = lessons.mapNotNull { lesson ->
        val lessonDay = dayMapping[lesson.day] ?: return@mapNotNull null

        // Parse lesson time (format: "HH:MM")
        val timeParts = lesson.time.split(":")
        if (timeParts.size != 2) return@mapNotNull null

        val lessonHour = timeParts[0].toIntOrNull() ?: return@mapNotNull null
        val lessonMinute = timeParts[1].toIntOrNull() ?: return@mapNotNull null
        val lessonTimeInMinutes = lessonHour * 60 + lessonMinute

        // Calculate days until this lesson
        val daysUntilLesson = when {
            lessonDay > currentDayOfWeek -> lessonDay - currentDayOfWeek
            lessonDay < currentDayOfWeek -> 7 - (currentDayOfWeek - lessonDay)
            else -> { // Same day
                if (lessonTimeInMinutes > currentTimeInMinutes) 0 // Today, later
                else 7 // Next week
            }
        }

        // Calculate total minutes until lesson
        val totalMinutesUntil = when (daysUntilLesson) {
            0 -> lessonTimeInMinutes - currentTimeInMinutes // Today
            else -> (daysUntilLesson * 24 * 60) + lessonTimeInMinutes - currentTimeInMinutes
        }

        if (totalMinutesUntil > 0) {
            Pair(lesson, totalMinutesUntil)
        } else null
    }.sortedBy { it.second }

    return upcomingLessons.firstOrNull()?.first
}

@Preview
@Composable
private fun HomeScreenPrev() {
    HomeScreen(modifier = Modifier.fillMaxSize())

}
