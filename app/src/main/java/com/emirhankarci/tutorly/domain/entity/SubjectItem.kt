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

// Tüm dersler listesi - renkleri standartlaştırılmış
private val allSubjects = mapOf(
    "Matematik" to SubjectInfo(R.drawable.subject_math, "Matematik", "Konular", Color(0xFF1976D2), Color(0xFF1976D2)),
    "Türkçe" to SubjectInfo(R.drawable.subject_turkish, "Türkçe", "Konular", Color(0xFFE91E63), Color(0xFFE91E63)),
    "Türk Dili ve Edebiyatı" to SubjectInfo(R.drawable.subject_turkish, "Türk Dili ve Edebiyatı", "Konular", Color(0xFFE91E63), Color(0xFFE91E63)),
    "Fizik" to SubjectInfo(R.drawable.subject_phy, "Fizik", "Konular", Color(0xFFFF5722), Color(0xFFFF5722)),
    "Kimya" to SubjectInfo(R.drawable.subject_chem, "Kimya", "Konular", Color(0xFF00BCD4), Color(0xFF00BCD4)),
    "Biyoloji" to SubjectInfo(R.drawable.subject_book, "Biyoloji", "Konular", Color(0xFF4CAF50), Color(0xFF4CAF50)),
    "Coğrafya" to SubjectInfo(R.drawable.subject_book, "Coğrafya", "Konular", Color(0xFFFFC107), Color(0xFFFFC107)),
    "Din Kültürü ve Ahlak Bilgisi" to SubjectInfo(R.drawable.subject_book, "Din Kültürü ve Ahlak Bilgisi", "Konular", Color(0xFFFF9800), Color(0xFFFF9800)),
    "Felsefe" to SubjectInfo(R.drawable.subject_book, "Felsefe", "Konular", Color(0xFF9C27B0), Color(0xFF9C27B0)),
    "Tarih" to SubjectInfo(R.drawable.subject_book, "Tarih", "Konular", Color(0xFF795548), Color(0xFF795548)),
    "İnkılap Tarihi ve Atatürkçülük" to SubjectInfo(R.drawable.subject_book, "İnkılap Tarihi ve Atatürkçülük", "Konular", Color(0xFF795548), Color(0xFF795548)),
    "Fen Bilimleri" to SubjectInfo(R.drawable.subject_book, "Fen Bilimleri", "Konular", Color(0xFF0288D1), Color(0xFF0288D1)),
    "Sosyal Bilgiler" to SubjectInfo(R.drawable.subject_book, "Sosyal Bilgiler", "Konular", Color(0xFF009688), Color(0xFF009688)),
    "Hayat Bilgisi" to SubjectInfo(R.drawable.subject_book, "Hayat Bilgisi", "Konular", Color(0xFF009688), Color(0xFF009688))
)

// Eski subjects değişkeni - geriye dönük uyumluluk için
val subjects = allSubjects.values.toList()

fun getSubjectsForGrade(grade: Int): List<SubjectInfo> {
    // chaptersByGradeAndSubject map'inden o sınıf için mevcut dersleri al
    val availableSubjects = chaptersByGradeAndSubject.keys
        .filter { it.grade == grade }
        .map { it.subject }
        .distinct()
        .mapNotNull { subjectName -> allSubjects[subjectName] }
        .sortedBy { 
            // Dersleri önemlerine göre sırala
            when (it.title) {
                "Matematik" -> 1
                "Türkçe", "Türk Dili ve Edebiyatı" -> 2
                "Fizik" -> 3
                "Kimya" -> 4
                "Biyoloji" -> 5
                "Fen Bilimleri" -> 6
                "Coğrafya" -> 7
                "Tarih", "İnkılap Tarihi ve Atatürkçülük" -> 8
                "Sosyal Bilgiler" -> 9
                "Hayat Bilgisi" -> 10
                "Din Kültürü ve Ahlak Bilgisi" -> 11
                "Felsefe" -> 12
                else -> 99
            }
        }
    
    return availableSubjects
}