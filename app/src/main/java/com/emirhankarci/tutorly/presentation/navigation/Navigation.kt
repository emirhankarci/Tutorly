package com.emirhankarci.tutorly.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import com.emirhankarci.tutorly.presentation.ui.components.MainScaffold
import com.emirhankarci.tutorly.presentation.ui.screen.*
import com.emirhankarci.tutorly.presentation.viewmodel.LoginViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Route.SplashScreen
    ) {
        // Splash Screen - First screen shown
        composable<Route.SplashScreen> {
            SplashScreen(
                onNavigateToAuth = {
                    navController.navigate(Route.AuthGraph) {
                        popUpTo(Route.SplashScreen) { inclusive = true }
                    }
                },
                onNavigateToMain = {
                    navController.navigate(Route.MainGraph) {
                        popUpTo(Route.SplashScreen) { inclusive = true }
                    }
                },
                onNavigateToProfileBuilding = {
                    navController.navigate(Route.ProfileBuildingScreen) {
                        popUpTo(Route.SplashScreen) { inclusive = true }
                    }
                }
            )
        }
        // Auth Graph - Authentication flow (No Scaffold)
        navigation<Route.AuthGraph>(
            startDestination = Route.OnboardingFlow
        ) {
            composable<Route.OnboardingFlow> {
                val authFlowViewModel: com.emirhankarci.tutorly.presentation.viewmodel.AuthFlowViewModel = hiltViewModel()
                val authFlowState by authFlowViewModel.uiState.collectAsState()

                OnboardingFlow(
                    onSignInSuccess = {
                        // Let AuthFlowViewModel handle the navigation decision
                        authFlowViewModel.onLoginSuccess()
                    }
                )

                // Listen for auth state changes and navigate accordingly
                LaunchedEffect(authFlowState.authFlowState) {
                    when (authFlowState.authFlowState) {
                        com.emirhankarci.tutorly.presentation.viewmodel.AuthFlowState.NEED_PROFILE_SETUP -> {
                            navController.navigate(Route.ProfileBuildingScreen) {
                                popUpTo(Route.AuthGraph) { inclusive = true }
                            }
                        }
                        com.emirhankarci.tutorly.presentation.viewmodel.AuthFlowState.AUTHENTICATED -> {
                            navController.navigate(Route.MainGraph) {
                                popUpTo(Route.AuthGraph) { inclusive = true }
                            }
                        }
                        else -> { /* Stay on auth graph */ }
                    }
                }
            }

            composable<Route.LoginScreen> {
                val authFlowViewModel: com.emirhankarci.tutorly.presentation.viewmodel.AuthFlowViewModel = hiltViewModel()
                val authFlowState by authFlowViewModel.uiState.collectAsState()

                LoginScreen(
                    onSignInSuccess = {
                        // Let AuthFlowViewModel handle the navigation decision
                        authFlowViewModel.onLoginSuccess()
                    }
                )

                // Listen for auth state changes and navigate accordingly
                LaunchedEffect(authFlowState.authFlowState) {
                    when (authFlowState.authFlowState) {
                        com.emirhankarci.tutorly.presentation.viewmodel.AuthFlowState.NEED_PROFILE_SETUP -> {
                            navController.navigate(Route.ProfileBuildingScreen) {
                                popUpTo(Route.AuthGraph) { inclusive = true }
                            }
                        }
                        com.emirhankarci.tutorly.presentation.viewmodel.AuthFlowState.AUTHENTICATED -> {
                            navController.navigate(Route.MainGraph) {
                                popUpTo(Route.AuthGraph) { inclusive = true }
                            }
                        }
                        else -> { /* Stay on auth graph */ }
                    }
                }
            }
        }

        // Main App Graph - Main application flow (With Scaffold)
        navigation<Route.MainGraph>(
            startDestination = Route.HomeScreen
        ) {
            composable<Route.HomeScreen> {
                val scheduleViewModel: com.emirhankarci.tutorly.presentation.viewmodel.ScheduleViewModel =
                    hiltViewModel(navController.getBackStackEntry(Route.MainGraph))
                MainScaffold(navController = navController) { modifier ->
                    HomeScreen(
                        modifier = modifier,
                        onNavigateToGradeSelection = {
                            navController.navigate(Route.GradeSelectionScreen)
                        },
                        onNavigateToSchedule = {
                            navController.navigate(Route.ScheduleScreen)
                        },
                        onNavigateToEnglishLearning = {
                            navController.navigate(Route.EnglishLearningScreen)
                        },
                        onNavigateToStudyWithImage = {
                            navController.navigate(Route.StudyWithImageScreen)
                        },
                        scheduleViewModel = scheduleViewModel
                    )
                }
            }

            composable<Route.LessonsScreen> {
                MainScaffold(navController = navController) { modifier ->
                    // This will be handled by BottomNavigationBar directly
                }
            }

            composable<Route.ScheduleScreen> {
                val scheduleViewModel: com.emirhankarci.tutorly.presentation.viewmodel.ScheduleViewModel =
                    hiltViewModel(navController.getBackStackEntry(Route.MainGraph))
                MainScaffold(navController = navController) { modifier ->
                    ScheduleScreen(
                        modifier = modifier,
                        onAddLesson = { day, time ->
                            navController.navigate(Route.AddLessonScreen(day, time))
                        },
                        onEditLesson = { lesson ->
                            navController.navigate(Route.EditLessonScreen(lesson.id))
                        },
                        onNavigateToProgress = {
                            navController.navigate(Route.ProgressScreen)
                        },
                        onNavigateToLessonPlanChat = {
                            navController.navigate(Route.LessonPlanChatScreen)
                        },
                        viewModel = scheduleViewModel
                    )
                }
            }

            composable<Route.ProgressScreen> {
                MainScaffold(navController = navController) { modifier ->
                    ProgressScreen(
                        modifier = modifier,
                        onBackPressed = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            composable<Route.SettingsScreen> {
                MainScaffold(navController = navController) { modifier ->
                    SettingsScreen(
                        modifier = modifier,
                        onFirestoreTest = {
                            navController.navigate(Route.FirestoreTestScreen)
                        },
                        onLogout = {
                            loginViewModel.signOut()
                            navController.navigate(Route.AuthGraph) {
                                popUpTo(Route.MainGraph) { inclusive = true }
                            }
                        }
                    )
                }
            }

            composable<Route.FirestoreTestScreen> {
                MainScaffold(navController = navController) { modifier ->
                    FirestoreTestScreen()
                }
            }

            composable<Route.GradeSelectionScreen> {
                MainScaffold(navController = navController) { modifier ->
                    GradeSelectionScreen(
                        modifier = modifier,
                        onGradeSelected = { gradeString ->
                            val gradeNumber = gradeString.replace("Grade ", "").toIntOrNull() ?: 9
                            navController.navigate(Route.SubjectSelectionScreen(gradeNumber))
                        }
                    )
                }
            }

            composable<Route.SubjectSelectionScreen> { backStackEntry ->
                val route = backStackEntry.toRoute<Route.SubjectSelectionScreen>()
                MainScaffold(navController = navController) { modifier ->
                    SubjectSelectionScreen(
                        grade = route.grade,
                        modifier = modifier,
                        onSubjectSelected = { subject ->
                            navController.navigate(Route.ChapterSelectionScreen(route.grade, subject))
                        },
                        onBackPressed = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            composable<Route.ChapterSelectionScreen> { backStackEntry ->
                val args = backStackEntry.arguments
                val grade = args?.getInt("grade") ?: 9
                val subject = args?.getString("subject") ?: "Matematik"
                MainScaffold(navController = navController) { modifier ->
                    ChapterSelectionScreen(
                        modifier = modifier,
                        grade = grade,
                        subject = subject,
                        onChapterSelected = { chapter ->
                            navController.navigate(Route.StudyMethodScreen(grade, subject, chapter))
                        },
                        onBackPressed = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            composable<Route.StudyMethodScreen> { backStackEntry ->
                val args = backStackEntry.arguments
                val grade = args?.getInt("grade") ?: 9
                val subject = args?.getString("subject") ?: "Matematik"
                val chapter = args?.getString("chapter") ?: "Sayılar ve İşlemler"
                MainScaffold(navController = navController) { modifier ->
                    StudyMethodScreen(
                        modifier = modifier,
                        grade = grade,
                        subject = subject,
                        chapter = chapter,
                        onAIChatClick = {
                            navController.navigate(Route.AIChatScreen(grade, subject, chapter))
                        },
                        onSummaryClick = {
                            navController.navigate(Route.SummaryScreen(grade, subject, chapter))
                        },
                        onQuizClick = { questionCount, questionType ->
                            navController.navigate(Route.QuizScreen(grade, subject, chapter, questionCount, questionType))
                        }
                    )
                }
            }

            composable<Route.ScheduleBuilderScreen> {
                MainScaffold(navController = navController) { modifier ->
                    ScheduleBuilderScreen(
                        modifier = modifier,
                        onSaved = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            composable<Route.AddLessonScreen> { backStackEntry ->
                val route = backStackEntry.toRoute<Route.AddLessonScreen>()
                val scheduleViewModel: com.emirhankarci.tutorly.presentation.viewmodel.ScheduleViewModel =
                    hiltViewModel(navController.getBackStackEntry(Route.MainGraph))
                MainScaffold(navController = navController) { modifier ->
                    AddLessonScreen(
                        modifier = modifier,
                        preSelectedDay = route.day,
                        preSelectedTime = route.time,
                        onSaveLesson = { lesson ->
                            scheduleViewModel.addLesson(lesson)
                            navController.popBackStack()
                        },
                        onBackPressed = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            composable<Route.EditLessonScreen> { backStackEntry ->
                val route = backStackEntry.toRoute<Route.EditLessonScreen>()
                val scheduleViewModel: com.emirhankarci.tutorly.presentation.viewmodel.ScheduleViewModel =
                    hiltViewModel(navController.getBackStackEntry(Route.MainGraph))
                val scheduleUiState by scheduleViewModel.uiState.collectAsState()
                val existingLesson = scheduleUiState.lessons.find { it.id == route.lessonId }

                MainScaffold(navController = navController) { modifier ->
                    AddLessonScreen(
                        modifier = modifier,
                        existingLesson = existingLesson,
                        onSaveLesson = { lesson ->
                            if (existingLesson != null) {
                                scheduleViewModel.updateLesson(existingLesson, lesson)
                            } else {
                                scheduleViewModel.addLesson(lesson)
                            }
                            navController.popBackStack()
                        },
                        onBackPressed = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            composable<Route.AIChatScreen> { backStackEntry ->
                val args = backStackEntry.arguments
                val grade = args?.getInt("grade") ?: 9
                val subject = args?.getString("subject") ?: "Matematik"
                val chapter = args?.getString("chapter") ?: "Sayılar ve İşlemler"
                MainScaffold(navController = navController) { modifier ->
                    AIChatScreen(
                        modifier = modifier,
                        grade = grade,
                        subject = subject,
                        chapter = chapter
                    )
                }
            }

            composable<Route.SummaryScreen> { backStackEntry ->
                val route = backStackEntry.toRoute<Route.SummaryScreen>()
                MainScaffold(navController = navController) { modifier ->
                    SummaryScreen(
                        modifier = modifier,
                        grade = route.grade,
                        subject = route.subject,
                        chapter = route.chapter,
                        onBackPressed = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            composable<Route.QuizScreen> { backStackEntry ->
                val route = backStackEntry.toRoute<Route.QuizScreen>()
                MainScaffold(navController = navController) { modifier ->
                    QuizScreen(
                        modifier = modifier,
                        grade = route.grade,
                        subject = route.subject,
                        chapter = route.chapter,
                        questionCount = route.questionCount,
                        questionType = route.questionType,
                        onBackPressed = {
                            navController.popBackStack()
                        },
                        onQuizCompleted = { score, total ->
                            // Quiz completed, stay on result screen
                            // User can choose to retake or go back
                        }
                    )
                }
            }

            composable<Route.EnglishLearningScreen> {
                MainScaffold(navController = navController) { modifier ->
                    EnglishLearningScreen(
                        modifier = modifier
                    )
                }
            }

            composable<Route.StudyWithImageScreen> {
                MainScaffold(navController = navController) { modifier ->
                    StudyWithImageScreen(
                        modifier = modifier,
                        onBackPressed = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            composable<Route.LessonPlanChatScreen> {
                val scheduleViewModel: com.emirhankarci.tutorly.presentation.viewmodel.ScheduleViewModel =
                    hiltViewModel(navController.getBackStackEntry(Route.MainGraph))
                MainScaffold(navController = navController) { modifier ->
                    LessonPlanChatScreen(
                        modifier = modifier,
                        onLessonPlanGenerated = { lessonPlan, parsedLessons ->
                            scheduleViewModel.setGeneratedLessonPlan(lessonPlan)
                            scheduleViewModel.addLessonsFromAI(parsedLessons)
                            navController.popBackStack()
                        }
                    )
                }
            }

        }

        // Profile Building Screen - Standalone outside main graph
        composable<Route.ProfileBuildingScreen> {
            val authFlowViewModel: com.emirhankarci.tutorly.presentation.viewmodel.AuthFlowViewModel = hiltViewModel()
            val authFlowState by authFlowViewModel.uiState.collectAsState()

            ProfileBuildingScreen(
                onProfileCompleted = { userProfile ->
                    authFlowViewModel.onProfileCompleted()
                },
                onNavigateBack = {
                    navController.navigate(Route.AuthGraph) {
                        popUpTo(Route.ProfileBuildingScreen) { inclusive = true }
                    }
                }
            )

            // Listen for auth state changes and navigate when profile is completed
            LaunchedEffect(authFlowState.authFlowState) {
                if (authFlowState.authFlowState == com.emirhankarci.tutorly.presentation.viewmodel.AuthFlowState.AUTHENTICATED) {
                    navController.navigate(Route.MainGraph) {
                        popUpTo(Route.ProfileBuildingScreen) { inclusive = true }
                    }
                }
            }
        }
    }
}