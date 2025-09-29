package com.emirhankarci.tutorly.domain.entity

import androidx.compose.ui.graphics.Color

data class ScheduleItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val subject: String,
    val time: String,
    val duration: String,
    val color: Color,
    val day: String,
    val notes: String = ""
)

object ScheduleData {
    val sampleSchedule = listOf(
        ScheduleItem(subject = "Matematik", time = "09:00", duration = "90 dk", color = Color(0xFF2196F3), day = "Pazartesi"),
        ScheduleItem(subject = "Fizik", time = "11:00", duration = "60 dk", color = Color(0xFFF44336), day = "Pazartesi"),
        ScheduleItem(subject = "Kimya", time = "14:00", duration = "90 dk", color = Color(0xFF4CAF50), day = "Salı"),
        ScheduleItem(subject = "Türkçe", time = "10:00", duration = "60 dk", color = Color(0xFF9C27B0), day = "Çarşamba"),
        ScheduleItem(subject = "Matematik", time = "15:00", duration = "90 dk", color = Color(0xFF2196F3), day = "Perşembe"),
        ScheduleItem(subject = "Biyoloji", time = "09:30", duration = "60 dk", color = Color(0xFFFF9800), day = "Cuma")
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