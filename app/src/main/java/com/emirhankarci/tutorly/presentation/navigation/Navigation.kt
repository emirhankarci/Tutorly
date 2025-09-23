package com.emirhankarci.tutorly.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.*
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
                    SettingsScreen(modifier = modifier)
                }

                composable<Route.GradeSelectionScreen> {
                    GradeSelectionScreen(
                        modifier = modifier,
                        onGradeSelected = { grade ->
                            navController.navigate(Route.SubjectSelectionScreen)
                        }
                    )
                }

                composable<Route.SubjectSelectionScreen> {
                    SubjectSelectionScreen(
                        modifier = modifier,
                        onSubjectSelected = { subject ->
                            navController.navigate(Route.ChapterSelectionScreen)
                        },
                        onBackPressed = {
                            navController.popBackStack()
                        }
                    )
                }

                composable<Route.ChapterSelectionScreen> {
                    ChapterSelectionScreen(
                        modifier = modifier,
                        onChapterSelected = { chapter ->
                            navController.navigate(Route.StudyMethodScreen)
                        },
                        onBackPressed = {
                            navController.popBackStack()
                        }
                    )
                }

                composable<Route.StudyMethodScreen> {
                    StudyMethodScreen(
                        modifier = modifier,
                        onAIChatClick = {
                            // TODO: Navigate to AI Chat screen
                        },
                        onSummaryClick = {
                            // TODO: Navigate to Summary screen
                        },
                        onQuizClick = {
                            // TODO: Navigate to Quiz screen
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
            }
        }
    }
}