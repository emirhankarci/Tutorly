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
    data class SubjectSelectionScreen(val grade: Int) : Route

    @Serializable
    data class ChapterSelectionScreen(val grade: Int, val subject: String) : Route

    @Serializable
    data object LessonsScreen : Route

    @Serializable
    data object ScheduleScreen : Route

    @Serializable
    data object SettingsScreen : Route

    @Serializable
    data class StudyMethodScreen(val grade: Int, val subject: String, val chapter: String) : Route

    @Serializable
    data object AddLessonScreen : Route

    @Serializable
    data class EditLessonScreen(val lessonId: String) : Route

    @Serializable
    data object FirestoreTestScreen : Route

    @Serializable
    data class AIChatScreen(val grade: Int, val subject: String, val chapter: String) : Route

    @Serializable
    data class SummaryScreen(val grade: Int, val subject: String, val chapter: String) : Route

    @Serializable
    data class QuizScreen(
        val grade: Int,
        val subject: String,
        val chapter: String,
        val questionCount: Int,
        val questionType: String
    ) : Route

}