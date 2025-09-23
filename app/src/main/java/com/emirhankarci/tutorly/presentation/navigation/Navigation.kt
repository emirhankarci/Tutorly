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
                        }
                    )
                }

                composable<Route.LessonsScreen> {
                    LessonsScreen(modifier = modifier)
                }

                composable<Route.ScheduleScreen> {
                    ScheduleScreen(modifier = modifier)
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
                            // TODO: Navigate to lesson detail screen when ready
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