package com.emirhankarci.tutorly.presentation.navigation

import android.graphics.drawable.Icon
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object AuthGraph : Route

    @Serializable
    data object MainGraph : Route

    @Serializable
    data object HomeScreen : Route

    @Serializable
    data object LoginScreen : Route

    @Serializable
    data object GradeSelectionScreen : Route

    @Serializable
    data object SubjectSelectionScreen : Route

    @Serializable
    data object ChapterSelectionScreen : Route

    @Serializable
    data object LessonsScreen : Route

    @Serializable
    data object ScheduleScreen : Route

    @Serializable
    data object SettingsScreen : Route

}