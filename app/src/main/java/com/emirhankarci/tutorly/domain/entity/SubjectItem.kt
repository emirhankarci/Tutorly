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
    SubjectInfo(R.drawable.subject_math, "Matematik", "7 Konu", Color(0xFF1976D2), Color(0xFF1976D2)),
    SubjectInfo(R.drawable.subject_turkish, "Türk Dili ve Edebiyati", "4 Konu", Color(0xFFE91E63), Color(0xFFE91E63)),
    SubjectInfo(R.drawable.subject_phy, "Fizik", "4 Konu", Color(0xFFE91E63), Color(0xFFE91E63)),
    SubjectInfo(R.drawable.subject_chem, "Kimya", "3 Konu", Color(0xFFFF5722), Color(0xFFFF5722)),
    SubjectInfo(R.drawable.subject_book, "Biyoloji", "2 Konu", Color(0xFFFF5722), Color(0xFFFF5722)),
    SubjectInfo(R.drawable.subject_book, "Coğrafya", "7 Konu", Color(0xFFFF5722), Color(0xFFFF5722)),
    SubjectInfo(R.drawable.subject_book, "Din Kültürü ve Ahlak Bilgisi", "5 Konu", Color(0xFF4CAF50), Color(0xFF4CAF50)),
    SubjectInfo(R.drawable.subject_book, "Tarih", "3 Konu", Color(0xFF795548), Color(0xFF795548)),
)