package com.emirhankarci.tutorly.domain.entity

import androidx.compose.ui.graphics.Color

data class GradeInfo(
    val number: Int,
    val title: String,
    val subtitle: String,
    val backgroundColor: Color,
    val borderColor: Color
)

val grades = listOf(
    GradeInfo(12, "Grade 12", "Senior Year", Color(0xFF1976D2), Color(0xFF1976D2)),
    GradeInfo(11, "Grade 11", "Junior Year", Color(0xFF4CAF50), Color(0xFF4CAF50)),
    GradeInfo(10, "Grade 10", "Sophomore", Color(0xFFE91E63), Color(0xFFE91E63)),
    GradeInfo(9, "Grade 9", "Freshman", Color(0xFFFF5722), Color(0xFFFF5722)),
    GradeInfo(8, "Grade 8", "Middle School", Color(0xFFFFC107), Color(0xFFFFC107)),
    GradeInfo(7, "Grade 7", "Middle School", Color(0xFF673AB7), Color(0xFF673AB7)),
    GradeInfo(6, "Grade 6", "Elementary", Color(0xFF009688), Color(0xFF009688)),
    GradeInfo(5, "Grade 5", "Elementary", Color(0xFFFF9800), Color(0xFFFF9800)),
    GradeInfo(4, "Grade 4", "Elementary", Color(0xFF795548), Color(0xFF795548)),
    GradeInfo(3, "Grade 3", "Elementary", Color(0xFF607D8B), Color(0xFF607D8B)),
    GradeInfo(2, "Grade 2", "Elementary", Color(0xFF9C27B0), Color(0xFF9C27B0)),
    GradeInfo(1, "Grade 1", "Elementary", Color(0xFFFF6B6B), Color(0xFFFF6B6B))
)