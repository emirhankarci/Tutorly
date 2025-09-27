package com.emirhankarci.tutorly.domain.entity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Create
import androidx.compose.ui.graphics.vector.ImageVector

data class TutorlyCardItem(
    val icon: ImageVector,
    val title: String,
    val description: String
)

val TutorlyCardItemResult = listOf(
    TutorlyCardItem(Icons.Default.Face, "Go to Subjects", "Get instant help with any topic"),
    TutorlyCardItem(Icons.Default.Create, "Study with Image", "Generate educational images"),
    TutorlyCardItem(Icons.Default.Home, "Study Plan", "Go to main screen"),
    TutorlyCardItem(Icons.Default.Settings, "Progress", "Adjust preferences"),
    )
