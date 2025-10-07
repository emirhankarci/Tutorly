package com.emirhankarci.tutorly.core.utils

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.emirhankarci.tutorly.R
import java.util.Calendar

data class GreetingData(
    val text: String,
    @DrawableRes val iconRes: Int,
    val iconColor: Color
)

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
            in 12..16 -> "Tünaydın"
            in 17..21 -> "İyi Akşamlar"
            else -> "İyi Geceler"
        }
    }

    fun getFullGreeting(userName: String, useTurkish: Boolean = true): String {
        val greeting = if (useTurkish) getTimeBasedGreetingTurkish() else getTimeBasedGreeting()
        return "$greeting, $userName"
    }

    fun getGreetingWithIcon(userName: String, useTurkish: Boolean = true): GreetingData {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hour) {
            in 5..11 -> GreetingData(
                text = if (useTurkish) "Günaydın, $userName" else "Good Morning, $userName",
                iconRes = R.drawable.morning,
                iconColor = Color(0xFFFDB813) // Yellow for morning
            )
            in 12..16 -> GreetingData(
                text = if (useTurkish) "Tünaydın, $userName" else "Good Afternoon, $userName",
                iconRes = R.drawable.noon,
                iconColor = Color(0xFFFDB813) // Yellow for noon
            )
            in 17..21 -> GreetingData(
                text = if (useTurkish) "İyi Akşamlar, $userName" else "Good Evening, $userName",
                iconRes = R.drawable.afternoon,
                iconColor = Color(0xFF5B7DB1) // Moon blue for evening
            )
            else -> GreetingData(
                text = if (useTurkish) "İyi Geceler, $userName" else "Good Night, $userName",
                iconRes = R.drawable.night,
                iconColor = Color(0xFF3D5A80) // Dark blue for night
            )
        }
    }
}