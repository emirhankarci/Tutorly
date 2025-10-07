package com.emirhankarci.tutorly.domain.entity

data class ProfileQuestion(
    val id: Int,
    val question: String,
    val answers: List<String>
)

data class ProfileAnswer(
    val questionId: Int,
    val selectedAnswer: String
)

data class UserProfile(
    val grade: String = "",
    val targetExams: String = "",
    val strongSubjects: String = "",
    val dailyStudyHours: String = "",
    val englishLevel: String = "",
    val ageRange: String = ""
)

object ProfileQuestionData {
    val questions = listOf(
        ProfileQuestion(
            id = 1,
            question = "Hangi sınıfta okuyorsun?",
            answers = listOf(
                "9. Sınıf",
                "10. Sınıf",
                "11. Sınıf",
                "12. Sınıf",
                "İlkokul",
                "Ortaokul",
                "Mezun",
            )
        ),
        ProfileQuestion(
            id = 2,
            question = "Hangi sınavlara hazırlanıyorsun?",
            answers = listOf(
                "TYT (Temel Yeterlilik Testi)",
                "AYT (Alan Yeterlilik Testi)",
                "YKS (Yükseköğretim Kurumları Sınavı)",
                "YDT (Yabancı Dil Testi)",
                "Okul Sınavlarım",
                "Henüz karar vermedim"
            )
        ),
        ProfileQuestion(
            id = 3,
            question = "Hangi derslerde kendinizi daha başarılı buluyorsunuz?",
            answers = listOf(
                "Matematik",
                "Fizik",
                "Kimya",
                "Biyoloji",
                "Türkçe",
                "Tarih",
                "Coğrafya",
                "İngilizce",
                "Edebiyat",
                "Din Kültürü"
            )
        ),
        ProfileQuestion(
            id = 4,
            question = "Günde ortalama kaç saat çalışıyorsun?",
            answers = listOf(
                "1-2 saat",
                "2-4 saat",
                "4-6 saat",
                "6-8 saat",
                "8+ saat",
                "Düzenli çalışmıyorum"
            )
        ),
        ProfileQuestion(
            id = 5,
            question = "İngilizce seviyeni nasıl değerlendiriyorsun?",
            answers = listOf(
                "Başlangıç (A1-A2)",
                "Orta Seviye (B1-B2)",
                "İleri Seviye (C1-C2)",
                "Ana dili seviyesinde",
                "Çok zayıf",
                "Değerlendirmek istemiyorum"
            )
        ),
        ProfileQuestion(
            id = 6,
            question = "Hangi yaş aralığı sana uygun?",
            answers = listOf(
                "7-12 yaş",
                "12-14 yaş",
                "14-16 yaş",
                "17-18 yaş",
                "19-22 yaş",
                "23-25 yaş",
                "25+ yaş"
            )
        )
    )
}