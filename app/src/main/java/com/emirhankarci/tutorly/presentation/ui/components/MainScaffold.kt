package com.emirhankarci.tutorly.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.emirhankarci.tutorly.presentation.navigation.Route
import com.emirhankarci.tutorly.domain.entity.GradeSubjectKey
import com.emirhankarci.tutorly.domain.entity.chaptersByGradeAndSubject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navController: NavController,
    content: @Composable (Modifier) -> Unit
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val arguments = navBackStackEntry.value?.arguments

    // Pre-compute gradient brushes for better performance
    val homeGradient = remember {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFF667eea), Color(0xFF764ba2), Color(0xFF4C9AFF)
            )
        )
    }
    val scheduleGradient = remember {
        Brush.linearGradient(
            colors = listOf(Color(0xFF8E24AA), Color(0xFF5E35B1))
        )
    }
    val addLessonGradient = remember {
        Brush.linearGradient(
            colors = listOf(Color(0xFF667eea), Color(0xFF764ba2))
        )
    }
    val settingsGradient = remember {
        Brush.linearGradient(
            colors = listOf(Color(0xFF424242), Color(0xFF616161))
        )
    }
    val studyWithImageGradient = remember {
        Brush.linearGradient(
            colors = listOf( Color(0xFF0723A4),Color(0xFF5E35B1))
        )
    }
    val studyGradient = remember {
        Brush.linearGradient(
            colors = listOf(Color(0xFF1976D2), Color(0xFF041D5A))
        )
    }
    val defaultGradient = remember {
        Brush.linearGradient(
            colors = listOf(Color(0xFF37474F), Color(0xFF455A64))
        )
    }
    val englishLearningGradient = remember {
        Brush.linearGradient(
            colors = listOf(Color(0xFFB30E0E), Color(0xFF6B0000))
        )
    }

    val brush = remember(currentRoute, arguments) {
        when {
            // SummaryScreen: use subject color as solid background (top bar area)
            currentRoute?.startsWith("${Route.SummaryScreen::class.qualifiedName}") == true -> {
                val grade = arguments?.getInt("grade") ?: 9
                val subject = arguments?.getString("subject") ?: "Matematik"
                val subjectColor = chaptersByGradeAndSubject[GradeSubjectKey(grade, subject)]
                    ?.firstOrNull()
                    ?.backgroundColor ?: Color(0xFF1976D2)
                Brush.linearGradient(colors = listOf(subjectColor, subjectColor))
            }
            currentRoute == Route.HomeScreen::class.qualifiedName -> homeGradient
            currentRoute == Route.EnglishLearningScreen::class.qualifiedName -> englishLearningGradient
            currentRoute == Route.StudyWithImageScreen::class.qualifiedName -> studyWithImageGradient
            currentRoute == Route.ScheduleScreen::class.qualifiedName -> scheduleGradient
            currentRoute == Route.LessonPlanChatScreen::class.qualifiedName -> scheduleGradient
            currentRoute == Route.AddLessonScreen::class.qualifiedName -> addLessonGradient
            currentRoute == Route.SettingsScreen::class.qualifiedName -> settingsGradient
            currentRoute?.startsWith("${Route.AIChatScreen::class.qualifiedName}") == true -> studyGradient
            currentRoute == Route.GradeSelectionScreen::class.qualifiedName ||
            currentRoute?.substringBefore("/") in setOf(
                Route.SubjectSelectionScreen::class.qualifiedName?.substringBefore("/"),
                Route.ChapterSelectionScreen::class.qualifiedName?.substringBefore("/"),
                Route.StudyMethodScreen::class.qualifiedName?.substringBefore("/"),
                Route.SummaryScreen::class.qualifiedName?.substringBefore("/"),
                Route.QuizScreen::class.qualifiedName?.substringBefore("/")
            ) -> studyGradient
            else -> defaultGradient
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(brush),
        topBar = {
            when {
                currentRoute == Route.HomeScreen::class.qualifiedName -> {
                    TopAppBar(
                        title = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Tutorly",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .background(brush)
                            .statusBarsPadding()
                    )
                }
                currentRoute == Route.EnglishLearningScreen::class.qualifiedName -> {
                    TopAppBar(
                        title = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "İngilizce Çalışma Ekranı",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .background(
                                            color = Color.White,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Geri",
                                        tint = Color(0xFFCC0000),
                                        modifier = Modifier
                                            .size(22.dp)
                                    )
                                }
                            }
                        },
                        actions = {
                            IconButton(onClick = { navController.navigate(Route.HomeScreen) }) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Ana Sayfa",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .background(brush)
                            .statusBarsPadding()
                    )
                }
                currentRoute == Route.StudyWithImageScreen::class.qualifiedName -> {
                    TopAppBar(
                        title = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Görsel ile Çalış",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .background(
                                            color = Color.White,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Geri",
                                        tint = Color(0xFF0C83B7),
                                        modifier = Modifier
                                            .size(22.dp)
                                    )
                                }
                            }
                        },
                        actions = {
                            IconButton(onClick = { navController.navigate(Route.HomeScreen) }) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Ana Sayfa",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .background(brush)
                            .statusBarsPadding()
                    )
                }
                currentRoute == Route.LessonPlanChatScreen::class.qualifiedName -> {
                    TopAppBar(
                        title = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Ders Planı Asistanı",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .background(
                                            color = Color.White,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Geri",
                                        tint = Color(0xFF8E24AA),
                                        modifier = Modifier
                                            .size(22.dp)
                                    )
                                }
                            }
                        },
                        actions = {
                            IconButton(onClick = { navController.navigate(Route.HomeScreen) }) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Ana Sayfa",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .background(brush)
                            .statusBarsPadding()
                    )
                }
                currentRoute?.startsWith("${Route.AIChatScreen::class.qualifiedName}") == true -> {
                    val grade = arguments?.getInt("grade") ?: 9
                    val subject = arguments?.getString("subject") ?: "Matematik"
                    val chapter = arguments?.getString("chapter") ?: "Konu"

                    TopAppBar(
                        title = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "AI Sohbet",
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "${grade}. Sınıf $subject - $chapter",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White.copy(alpha = 0.9f)
                                    )
                                }
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .background(
                                            color = Color.White,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Geri",
                                        tint = Color(0xFF0C83B7),
                                        modifier = Modifier
                                            .size(22.dp)
                                    )
                                }
                            }
                        },
                        actions = {
                            IconButton(onClick = { navController.navigate(Route.HomeScreen) }) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Ana Sayfa",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .background(brush)
                            .statusBarsPadding()
                    )
                }
                currentRoute == Route.GradeSelectionScreen::class.qualifiedName ||
                currentRoute?.startsWith("${Route.SubjectSelectionScreen::class.qualifiedName}") == true ||
                currentRoute?.startsWith("${Route.ChapterSelectionScreen::class.qualifiedName}") == true ||
                currentRoute?.startsWith("${Route.StudyMethodScreen::class.qualifiedName}") == true ||
                currentRoute?.startsWith("${Route.SummaryScreen::class.qualifiedName}") == true ||
                currentRoute?.startsWith("${Route.QuizScreen::class.qualifiedName}") == true -> {
                    TopAppBar(
                        title = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                when {
                                    currentRoute?.startsWith("${Route.SummaryScreen::class.qualifiedName}") == true -> {
                                        val grade = arguments?.getInt("grade") ?: 9
                                        val subject = arguments?.getString("subject") ?: "Matematik"
                                        val chapter = arguments?.getString("chapter") ?: "Konu"

                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "Konu Özeti",
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                            Text(
                                                text = "${grade}. Sınıf $subject - $chapter",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = Color.White.copy(alpha = 0.9f),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                    currentRoute?.startsWith("${Route.QuizScreen::class.qualifiedName}") == true -> {
                                        val grade = arguments?.getInt("grade") ?: 9
                                        val subject = arguments?.getString("subject") ?: "Matematik"
                                        val chapter = arguments?.getString("chapter") ?: "Konu"

                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "Quiz",
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                            Text(
                                                text = "${grade}. Sınıf $subject - $chapter",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = Color.White.copy(alpha = 0.9f)
                                            )
                                        }
                                    }
                                    else -> {
                                        Text(
                                            text = when {
                                                currentRoute == Route.GradeSelectionScreen::class.qualifiedName -> "Sınıf Seçim Ekranı"
                                                currentRoute?.startsWith("${Route.SubjectSelectionScreen::class.qualifiedName}") == true -> "Ders Seçim Ekranı"
                                                currentRoute?.startsWith("${Route.ChapterSelectionScreen::class.qualifiedName}") == true -> "Konu Seçim Ekranı"
                                                currentRoute?.startsWith("${Route.StudyMethodScreen::class.qualifiedName}") == true -> "Çalışma Yöntemi"
                                                else -> "Tutorly"
                                            },
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .background(
                                            color = Color.White,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Geri",
                                        tint = Color(0xFF0C83B7),
                                        modifier = Modifier
                                            .size(22.dp)
                                    )
                                }
                            }
                        },
                        actions = {
                            Box(modifier = Modifier.size(48.dp))
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .background(brush)
                            .statusBarsPadding()
                    )
                }
                else -> {
                    TopAppBar(
                        title = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = when {
                                        currentRoute == Route.ScheduleScreen::class.qualifiedName -> "Ders Programı"
                                        currentRoute == Route.AddLessonScreen::class.qualifiedName -> "Ders Planı Asistanı"
                                        currentRoute == Route.LessonPlanChatScreen::class.qualifiedName -> "Ders Planı Asistanı"
                                        currentRoute == Route.SettingsScreen::class.qualifiedName -> "Ayarlar"
                                        else -> "Tutorly"
                                    },
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .background(brush)
                            .statusBarsPadding()
                    )
                }
            }
        },
        bottomBar = {
            // Hide bottom navigation for AI chat screens
            val hideBottomBar = currentRoute == Route.EnglishLearningScreen::class.qualifiedName ||
                    currentRoute?.startsWith("${Route.AIChatScreen::class.qualifiedName}") == true ||
                    currentRoute == Route.StudyWithImageScreen::class.qualifiedName ||
                    currentRoute == Route.LessonPlanChatScreen::class.qualifiedName

            if (!hideBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        content(Modifier.padding(paddingValues))
    }
}