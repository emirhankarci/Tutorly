package com.emirhankarci.tutorly.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
    val currentDestination = navBackStackEntry.value?.destination

    NavigationBar {
        bottomNavItems.forEach { item ->
            val isSelected = if (item.route == Route.LessonsScreen) {
                // For "Dersler" button, also consider lesson-related screens as selected
                currentDestination?.hierarchy?.any { destination ->
                    destination.route == Route.LessonsScreen::class.qualifiedName ||
                    destination.route == Route.GradeSelectionScreen::class.qualifiedName ||
                    destination.route == Route.SubjectSelectionScreen::class.qualifiedName ||
                    destination.route == Route.ChapterSelectionScreen::class.qualifiedName ||
                    destination.route == Route.StudyMethodScreen::class.qualifiedName
                } == true
            } else {
                currentDestination?.hierarchy?.any {
                    it.route == item.route::class.qualifiedName
                } == true
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
                    when (item.route) {
                        Route.LessonsScreen -> {
                            navController.navigate(Route.GradeSelectionScreen) {
                                popUpTo(Route.HomeScreen) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        Route.HomeScreen -> {
                            navController.navigate(Route.HomeScreen) {
                                popUpTo(Route.HomeScreen) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                        else -> {
                            navController.navigate(item.route) {
                                popUpTo(Route.HomeScreen) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                }
            )
        }
    }
}