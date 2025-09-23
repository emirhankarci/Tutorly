package com.emirhankarci.tutorly.domain.entity

import androidx.compose.ui.graphics.Color
import com.emirhankarci.tutorly.R

data class ChapterInfo(
    val title: String,
    val description: String,
    val lessonCount: Int,
    val backgroundColor: Color,
    val borderColor: Color
)

val chapters = listOf(
    ChapterInfo("Sayılar ve Cebir", "Doğal sayılar, tam sayılar ve rasyonel sayılar", 8, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Geometri", "Nokta, doğru, düzlem ve açılar", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Veri Analizi", "İstatistik ve veri yorumlama", 4, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Olasılık", "Olay ve olasılık hesaplamaları", 5, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Fonksiyonlar", "Doğrusal ve ikinci dereceden fonksiyonlar", 7, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Türev", "Türev kavramı ve uygulamaları", 9, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("İntegral", "Belirsiz ve belirli integral", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Limit", "Limit kavramı ve süreklilik", 5, Color(0xFF1976D2), Color(0xFF1976D2))
)