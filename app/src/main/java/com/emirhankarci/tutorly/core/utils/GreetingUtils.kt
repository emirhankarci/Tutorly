package com.emirhankarci.tutorly.core.utils

import java.util.Calendar

object GreetingUtils {

    fun getTimeBasedGreeting(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hour) {
            in 5..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            in 17..21 -> "Good Evening"
            else -> "Good Night"
        }
    }

    fun getTimeBasedGreetingTurkish(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hour) {
            in 5..11 -> "Günaydın"
            in 12..16 -> "İyi Öğlen"
            in 17..21 -> "İyi Akşamlar"
            else -> "İyi Geceler"
        }
    }

    fun getFullGreeting(userName: String, useTurkish: Boolean = true): String {
        val greeting = if (useTurkish) getTimeBasedGreetingTurkish() else getTimeBasedGreeting()
        return "$greeting, $userName"
    }
}