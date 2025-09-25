package com.emirhankarci.tutorly.data.model

data class QuizRequest(
    val sinif: Int,
    val ders: String,
    val konu: String,
    val soru_sayisi: Int,
    val soru_tipi: String, // "coktan_secmeli" | "acik_uclu"
    val zorluk: String = "orta", // "kolay" | "orta" | "zor"
    val dil: String = "tr"
)

data class MultipleChoiceQuestionDto(
    val soru: String,
    val a: String,
    val b: String,
    val c: String,
    val d: String,
    val cevap: String,
    val aciklama: String
)

data class OpenEndedQuestionDto(
    val soru: String,
    val cevap: String,
    val aciklama: String
)

data class QuizInfo(
    val sinif: Int,
    val ders: String,
    val original_ders: String?,
    val konu: String,
    val zorluk: String,
    val dil: String,
    val generated_by: String?,
    val model: String?
)

data class QuizResponse(
    val quiz_info: QuizInfo,
    val sorular: List<Map<String, Any?>>, // raw to support both types; mapping done in repository
    val total_soru: Int,
    val soru_tipi: String,
    val created_at: String
)


