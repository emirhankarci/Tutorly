package com.emirhankarci.tutorly.domain.entity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Create
import androidx.compose.ui.graphics.vector.ImageVector

data class TutorlyCardItem(
    val icon: ImageVector,
    val title: String,
    val description: String
)

val TutorlyCardItemResult = listOf(
    TutorlyCardItem(Icons.Default.Face, "Konulara Git", "Her konuda anında yardım al"),
    TutorlyCardItem(Icons.Default.Create, "Görsel ile Çalış", "Eğitici görseller oluştur"),
    TutorlyCardItem(Icons.Default.DateRange, "Çalışma Planı", "Ders programını görüntüle"),
    TutorlyCardItem(Icons.Default.Person, "Profil", "Ayarlara git"),
    )
