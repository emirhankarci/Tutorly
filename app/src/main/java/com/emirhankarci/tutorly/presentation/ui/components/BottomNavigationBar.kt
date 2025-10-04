package com.emirhankarci.tutorly.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.emirhankarci.tutorly.presentation.navigation.Route

data class BottomNavItem(
    val route: Route,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = Route.HomeScreen,
        title = "Ana Sayfa",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavItem(
        route = Route.LessonsScreen,
        title = "Dersler",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavItem(
        route = Route.ScheduleScreen,
        title = "Ders Programı",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavItem(
        route = Route.SettingsScreen,
        title = "Ayarlar",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
)

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    // Pre-compute lesson-related routes for better performance
    val lessonRelatedRoutes = remember {
        setOf(
            Route.LessonsScreen::class.qualifiedName,
            Route.GradeSelectionScreen::class.qualifiedName,
            Route.SubjectSelectionScreen::class.qualifiedName?.substringBefore("/"),
            Route.ChapterSelectionScreen::class.qualifiedName?.substringBefore("/"),
            Route.StudyMethodScreen::class.qualifiedName?.substringBefore("/"),
            Route.AIChatScreen::class.qualifiedName?.substringBefore("/"),
            Route.SummaryScreen::class.qualifiedName?.substringBefore("/"),
            Route.QuizScreen::class.qualifiedName?.substringBefore("/")
        ).filterNotNull().toSet()
    }

    NavigationBar {
        bottomNavItems.forEach { item ->
            val isSelected = remember(currentRoute) {
                when (item.route) {
                    Route.LessonsScreen -> {
                        currentRoute?.substringBefore("/") in lessonRelatedRoutes
                    }
                    else -> {
                        currentRoute == item.route::class.qualifiedName
                    }
                }
            }

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = isSelected,
                onClick = {
                    // Optimized navigation with minimal operations
                    when (item.route) {
                        Route.LessonsScreen -> {
                            if (currentRoute != Route.GradeSelectionScreen::class.qualifiedName) {
                                navController.navigate(Route.GradeSelectionScreen) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                        Route.HomeScreen -> {
                            navController.navigate(Route.HomeScreen) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        else -> {
                            if (currentRoute != item.route::class.qualifiedName) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}