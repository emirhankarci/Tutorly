package com.emirhankarci.tutorly.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import com.emirhankarci.tutorly.presentation.ui.components.MainScaffold
import com.emirhankarci.tutorly.presentation.ui.screen.*

@Composable
fun Navigation() {
    val navController = rememberNavController()

    MainScaffold(navController = navController) { modifier ->
        NavHost(
            navController = navController,
            startDestination = Route.MainGraph
        ) {
            // Auth Graph - Authentication flow
            navigation<Route.AuthGraph>(
                startDestination = Route.LoginScreen
            ) {
                composable<Route.LoginScreen> {
                    // TODO: Add login screen when ready
                }
            }

            // Main App Graph - Main application flow
            navigation<Route.MainGraph>(
                startDestination = Route.HomeScreen
            ) {
                composable<Route.HomeScreen> {
                    HomeScreen(
                        modifier = modifier,
                        onNavigateToGradeSelection = {
                            navController.navigate(Route.GradeSelectionScreen)
                        },
                        onNavigateToSchedule = {
                            navController.navigate(Route.ScheduleScreen)
                        }
                    )
                }

                composable<Route.LessonsScreen> {
                    // This will be handled by BottomNavigationBar directly
                }

                composable<Route.ScheduleScreen> {
                    ScheduleScreen(
                        modifier = modifier,
                        onAddLesson = {
                            navController.navigate(Route.AddLessonScreen)
                        },
                        onEditLesson = { lesson ->
                            // For demo purposes, navigate to edit with lesson subject as ID
                            navController.navigate(Route.EditLessonScreen(lesson.subject))
                        }
                    )
                }

                composable<Route.SettingsScreen> {
                    SettingsScreen(
                        modifier = modifier,
                        onFirestoreTest = {
                            navController.navigate(Route.FirestoreTestScreen)
                        }
                    )
                }

                composable<Route.FirestoreTestScreen> {
                    FirestoreTestScreen()
                }

                composable<Route.GradeSelectionScreen> {
                    GradeSelectionScreen(
                        modifier = modifier,
                        onGradeSelected = { gradeString ->
                            val gradeNumber = gradeString.replace("Grade ", "").toIntOrNull() ?: 9
                            navController.navigate(Route.SubjectSelectionScreen(gradeNumber))
                        }
                    )
                }

                composable<Route.SubjectSelectionScreen> { backStackEntry ->
                    val route = backStackEntry.toRoute<Route.SubjectSelectionScreen>()
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

                composable<Route.ChapterSelectionScreen> { backStackEntry ->
                    val args = backStackEntry.arguments
                    val grade = args?.getInt("grade") ?: 9
                    val subject = args?.getString("subject") ?: "Matematik"
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

                composable<Route.StudyMethodScreen> { backStackEntry ->
                    val args = backStackEntry.arguments
                    val grade = args?.getInt("grade") ?: 9
                    val subject = args?.getString("subject") ?: "Matematik"
                    val chapter = args?.getString("chapter") ?: "Sayılar ve İşlemler"
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

                composable<Route.AddLessonScreen> {
                    AddLessonScreen(
                        modifier = modifier,
                        onSaveLesson = { lesson ->
                            // TODO: Save lesson to data source
                            navController.popBackStack()
                        },
                        onBackPressed = {
                            navController.popBackStack()
                        }
                    )
                }

                composable<Route.EditLessonScreen> { backStackEntry ->
                    val editRoute = backStackEntry.arguments?.getString("lessonId")
                    EditLessonScreen(
                        modifier = modifier,
                        lesson = com.emirhankarci.tutorly.domain.entity.ScheduleData.sampleSchedule.find { it.subject == editRoute },
                        onSaveLesson = { lesson ->
                            // TODO: Update lesson in data source
                            navController.popBackStack()
                        },
                        onDeleteLesson = { lesson ->
                            // TODO: Delete lesson from data source
                            navController.popBackStack()
                        },
                        onBackPressed = {
                            navController.popBackStack()
                        }
                    )
                }

                composable<Route.AIChatScreen> { backStackEntry ->
                    val args = backStackEntry.arguments
                    val grade = args?.getInt("grade") ?: 9
                    val subject = args?.getString("subject") ?: "Matematik"
                    val chapter = args?.getString("chapter") ?: "Sayılar ve İşlemler"
                    AIChatScreen(
                        modifier = modifier,
                        grade = grade,
                        subject = subject,
                        chapter = chapter
                    )
                }

                composable<Route.SummaryScreen> { backStackEntry ->
                    val route = backStackEntry.toRoute<Route.SummaryScreen>()
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

                composable<Route.QuizScreen> { backStackEntry ->
                    val route = backStackEntry.toRoute<Route.QuizScreen>()
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
        }
    }
}