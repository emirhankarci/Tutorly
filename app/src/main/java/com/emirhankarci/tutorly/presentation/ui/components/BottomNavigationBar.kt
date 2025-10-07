package com.emirhankarci.tutorly.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.emirhankarci.tutorly.R
import com.emirhankarci.tutorly.presentation.navigation.Route

sealed interface IconSource {
    data class Vector(val imageVector: ImageVector) : IconSource
    data class Drawable(@DrawableRes val resId: Int) : IconSource
}

data class BottomNavItem(
    val route: Route,
    val title: String,
    val selectedIcon: IconSource,
    val unselectedIcon: IconSource
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = Route.HomeScreen,
        title = "Ana Sayfa",
        selectedIcon = IconSource.Vector(Icons.Filled.Home),
        unselectedIcon = IconSource.Vector(Icons.Outlined.Home)
    ),
    BottomNavItem(
        route = Route.LessonsScreen,
        title = "Dersler",
        selectedIcon = IconSource.Drawable(R.drawable.acik_kitap),
        unselectedIcon = IconSource.Drawable(R.drawable.kapali_kitap)
    ),
    BottomNavItem(
        route = Route.ScheduleScreen,
        title = "Ders Programı",
        selectedIcon = IconSource.Drawable(R.drawable.ders_programi),
        unselectedIcon = IconSource.Drawable(R.drawable.ders_programi)
    ),
    BottomNavItem(
        route = Route.SettingsScreen,
        title = "Ayarlar",
        selectedIcon = IconSource.Vector(Icons.Filled.Settings),
        unselectedIcon = IconSource.Vector(Icons.Outlined.Settings)
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

    NavigationBar(
        containerColor = Color(0xFFFAFAFA)
    ) {
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
                    val iconSource = if (isSelected) item.selectedIcon else item.unselectedIcon
                    when (iconSource) {
                        is IconSource.Vector -> Icon(
                            imageVector = iconSource.imageVector,
                            contentDescription = item.title
                        )
                        is IconSource.Drawable -> Icon(
                            painter = painterResource(id = iconSource.resId),
                            contentDescription = item.title
                        )
                    }
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