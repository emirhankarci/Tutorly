package com.emirhankarci.tutorly.domain.entity

import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName

@IgnoreExtraProperties
data class AppUser(
    val uid: String = "",
    val displayName: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    val firstName: String = "",
    val lastName: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    @get:PropertyName("schedule_items")
    @set:PropertyName("schedule_items")
    var scheduleItems: List<UserScheduleItem> = emptyList(),
    @get:PropertyName("study_progress")
    @set:PropertyName("study_progress")
    var studyProgress: UserProgress = UserProgress()
) {
    companion object {
        fun empty() = AppUser()
    }

    val fullName: String
        get() = when {
            firstName.isNotEmpty() && lastName.isNotEmpty() -> "$firstName $lastName"
            displayName.isNotEmpty() -> displayName
            else -> "User"
        }

    val greetingName: String
        get() = when {
            firstName.isNotEmpty() -> firstName
            displayName.isNotEmpty() -> displayName.split(" ").firstOrNull() ?: displayName
            else -> "there"
        }
}

data class UserScheduleItem(
    val id: String = "",
    val subject: String = "",
    val day: String = "",
    val time: String = "",
    val duration: String = "",
    val location: String = "",
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    @get:PropertyName("completed")
    val isCompleted: Boolean = false
)

data class UserProgress(
    @PropertyName("total_study_time")
    val totalStudyTime: Long = 0L, // in minutes
    @PropertyName("completed_lessons")
    val completedLessons: Int = 0,
    @PropertyName("weekly_goal")
    val weeklyGoal: Int = 300, // minutes per week
    @PropertyName("current_streak")
    val currentStreak: Int = 0,
    @PropertyName("best_streak")
    val bestStreak: Int = 0,
    @PropertyName("last_study_date")
    val lastStudyDate: Long = 0L,
    @PropertyName("subjects_studied")
    val subjectsStudied: Map<String, Int> = emptyMap() // subject -> minutes studied
)