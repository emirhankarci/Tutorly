package com.emirhankarci.tutorly.domain.entity

import androidx.compose.ui.graphics.Color
import com.emirhankarci.tutorly.R

data class SubjectInfo(
    val icon: Int,
    val title: String,
    val chapter: String,
    val backgroundColor: Color,
    val borderColor: Color
)

val subjects = listOf(
    SubjectInfo(R.drawable.subject_math, "Matematik", "12 Konu", Color(0xFF1976D2), Color(0xFF1976D2)),
    SubjectInfo(R.drawable.subject_turkish, "Türkçe", "8 Konu", Color(0xFF1976D2), Color(0xFF1976D2)),
    SubjectInfo(R.drawable.subject_phy, "Fizik", "15 Konu", Color(0xFF1976D2), Color(0xFF1976D2)),
    SubjectInfo(R.drawable.subject_chem, "Kimya", "17 Konu", Color(0xFF1976D2), Color(0xFF1976D2)),
)