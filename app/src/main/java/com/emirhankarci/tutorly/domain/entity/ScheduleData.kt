package com.emirhankarci.tutorly.domain.entity

import androidx.compose.ui.graphics.Color

data class ScheduleItem(
    val subject: String,
    val time: String,
    val duration: String,
    val color: Color,
    val day: String
)

object ScheduleData {
    val sampleSchedule = listOf(
        ScheduleItem("Matematik", "09:00", "90 dk", Color(0xFF2196F3), "Pazartesi"),
        ScheduleItem("Fizik", "11:00", "60 dk", Color(0xFFF44336), "Pazartesi"),
        ScheduleItem("Kimya", "14:00", "90 dk", Color(0xFF4CAF50), "Salı"),
        ScheduleItem("Türkçe", "10:00", "60 dk", Color(0xFF9C27B0), "Çarşamba"),
        ScheduleItem("Matematik", "15:00", "90 dk", Color(0xFF2196F3), "Perşembe"),
        ScheduleItem("Biyoloji", "09:30", "60 dk", Color(0xFFFF9800), "Cuma")
    )

    val weekDays = listOf("Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar")

    fun getTodaysLessons(): List<ScheduleItem> {
        // For demo purposes, showing Monday's lessons as "today"
        return sampleSchedule.filter { it.day == "Pazartesi" }
    }

    fun getNextLesson(): ScheduleItem? {
        // Return the first lesson for demo purposes
        return sampleSchedule.firstOrNull()
    }

    fun getTotalWeeklyDuration(): Int {
        return sampleSchedule.sumOf { it.duration.replace(" dk", "").toInt() }
    }
}