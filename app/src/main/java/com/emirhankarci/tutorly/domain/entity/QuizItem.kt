package com.emirhankarci.tutorly.domain.entity

data class QuizQuestion(
    val id: Int,
    val question: String,
    val type: QuestionType,
    val options: List<String> = emptyList(), // For test questions
    val correctAnswerIndex: Int = -1, // For test questions (index of correct option)
    val correctAnswer: String = "", // For open-ended questions
    val explanation: String
)

enum class QuestionType {
    TEST, OPEN_ENDED
}

data class QuizAnswer(
    val questionId: Int,
    val selectedOption: Int? = null, // For test questions
    val writtenAnswer: String? = null, // For open-ended questions
    val isCorrect: Boolean = false,
    val isAnswered: Boolean = false
)

data class QuizState(
    val currentQuestionIndex: Int = 0,
    val answers: MutableMap<Int, QuizAnswer> = mutableMapOf(),
    val isQuizCompleted: Boolean = false,
    val score: Int = 0
)

// Template questions for different subjects
fun getQuizQuestions(subject: String, chapter: String, questionType: QuestionType, count: Int): List<QuizQuestion> {
    val allQuestions = when (subject) {
        "Matematik" -> getMathQuizQuestions(chapter, questionType)
        "Fizik" -> getPhysicsQuizQuestions(chapter, questionType)
        "Kimya" -> getChemistryQuizQuestions(chapter, questionType)
        "Biyoloji" -> getBiologyQuizQuestions(chapter, questionType)
        "Tarih" -> getHistoryQuizQuestions(chapter, questionType)
        "Coğrafya" -> getGeographyQuizQuestions(chapter, questionType)
        "Türk Dili ve Edebiyatı" -> getTurkishQuizQuestions(chapter, questionType)
        "Felsefe" -> getPhilosophyQuizQuestions(chapter, questionType)
        "Din Kültürü ve Ahlak Bilgisi" -> getReligionQuizQuestions(chapter, questionType)
        else -> getDefaultQuizQuestions(subject, chapter, questionType)
    }

    return allQuestions.take(count)
}

private fun getMathQuizQuestions(chapter: String, type: QuestionType): List<QuizQuestion> {
    return when (type) {
        QuestionType.TEST -> listOf(
            QuizQuestion(
                id = 1,
                question = "f(x) = 2x + 3 fonksiyonunda f(4) değeri kaçtır?",
                type = QuestionType.TEST,
                options = listOf("8", "11", "9", "10"),
                correctAnswerIndex = 1,
                explanation = "f(4) = 2(4) + 3 = 8 + 3 = 11. Fonksiyonda x yerine 4 yazıp işlem yapılır."
            ),
            QuizQuestion(
                id = 2,
                question = "y = x² - 4x + 3 parabolünün tepe noktasının x koordinatı kaçtır?",
                type = QuestionType.TEST,
                options = listOf("1", "2", "3", "4"),
                correctAnswerIndex = 1,
                explanation = "Tepe noktası x = -b/2a formülü ile bulunur. x = -(-4)/2(1) = 4/2 = 2"
            ),
            QuizQuestion(
                id = 3,
                question = "f(x) = x³ fonksiyonunun tersinin denklemi nedir?",
                type = QuestionType.TEST,
                options = listOf("f⁻¹(x) = ∛x", "f⁻¹(x) = x²", "f⁻¹(x) = 1/x", "f⁻¹(x) = 3x"),
                correctAnswerIndex = 0,
                explanation = "f(x) = x³ ise y = x³, x = ∛y, dolayısıyla f⁻¹(x) = ∛x"
            ),
            QuizQuestion(
                id = 4,
                question = "(f + g)(x) = x² + 3x + 2 ve f(x) = x² ise g(x) kaçtır?",
                type = QuestionType.TEST,
                options = listOf("3x + 2", "x + 2", "3x", "2x + 2"),
                correctAnswerIndex = 0,
                explanation = "g(x) = (f + g)(x) - f(x) = (x² + 3x + 2) - x² = 3x + 2"
            ),
            QuizQuestion(
                id = 5,
                question = "f(x) = |x - 2| fonksiyonu hangi noktada türevlenebilir değildir?",
                type = QuestionType.TEST,
                options = listOf("x = 0", "x = 2", "x = 1", "x = -2"),
                correctAnswerIndex = 1,
                explanation = "Mutlak değer fonksiyonu, içindeki ifadenin sıfır olduğu noktada türevlenebilir değildir. x - 2 = 0, yani x = 2"
            )
        )
        QuestionType.OPEN_ENDED -> listOf(
            QuizQuestion(
                id = 6,
                question = "Fonksiyon kavramını tanımlayın ve günlük hayattan bir örnek verin.",
                type = QuestionType.OPEN_ENDED,
                correctAnswer = "Fonksiyon, iki küme arasında kurulan özel bir ilişkidir. Her x değeri için yalnızca bir y değeri bulunur.",
                explanation = "Fonksiyon, bire-bir eşleşme kuralıdır. Örnek: Bir kişinin boyu (x) ile kilosu (y) arasındaki ilişki."
            ),
            QuizQuestion(
                id = 7,
                question = "İkinci dereceden bir fonksiyonun grafiği neden parabol şeklindedir?",
                type = QuestionType.OPEN_ENDED,
                correctAnswer = "İkinci dereceden fonksiyonların x² terimi bulunur ve bu terim fonksiyona parabolik özellik kazandırır.",
                explanation = "x² terimi simetrik bir yapı oluşturur ve maksimum ya da minimum değere sahip sürekli bir eğri meydana getirir."
            ),
            QuizQuestion(
                id = 8,
                question = "Bir fonksiyonun tersinin bulunabilmesi için hangi koşulların sağlanması gerekir?",
                type = QuestionType.OPEN_ENDED,
                correctAnswer = "Fonksiyonun bire-bir (injektif) ve örten (süjektif) olması, yani bijektif olması gerekir.",
                explanation = "Bire-bir: Her y değeri için sadece bir x değeri. Örten: Her y değeri için bir x değeri bulunur."
            ),
            QuizQuestion(
                id = 9,
                question = "Fonksiyon kompozisyonu nedir ve nasıl hesaplanır?",
                type = QuestionType.OPEN_ENDED,
                correctAnswer = "Fonksiyon kompozisyonu (f∘g)(x) = f(g(x)) şeklinde tanımlanır. İç fonksiyon önce, dış fonksiyon sonra uygulanır.",
                explanation = "Kompozisyon, fonksiyonların ardışık uygulanmasıdır. g(x) sonucunu f fonksiyonuna girdi olarak veririz."
            ),
            QuizQuestion(
                id = 10,
                question = "Doğrusal fonksiyonların grafiklerinin eğimi fonksiyonun hangi katsayısı ile belirlenir?",
                type = QuestionType.OPEN_ENDED,
                correctAnswer = "f(x) = ax + b doğrusal fonksiyonunda eğim 'a' katsayısı ile belirlenir.",
                explanation = "a > 0 ise fonksiyon artan, a < 0 ise azalan, |a| değeri eğimin dikliğini belirtir."
            )
        )
    }
}

private fun getPhysicsQuizQuestions(chapter: String, type: QuestionType): List<QuizQuestion> {
    return when (type) {
        QuestionType.TEST -> listOf(
            QuizQuestion(
                id = 1,
                question = "Newton'un ikinci yasası hangi formülle ifade edilir?",
                type = QuestionType.TEST,
                options = listOf("F = ma", "F = mv", "F = m/a", "F = a/m"),
                correctAnswerIndex = 0,
                explanation = "Newton'un ikinci yasası: Net kuvvet = kütle × ivme, yani F = ma"
            ),
            QuizQuestion(
                id = 2,
                question = "Serbest düşmede ivme değeri kaçtır?",
                type = QuestionType.TEST,
                options = listOf("9.8 m/s²", "10 m/s²", "9 m/s²", "8 m/s²"),
                correctAnswerIndex = 0,
                explanation = "Yerçekimi ivmesi g = 9.8 m/s² (yaklaşık 10 m/s² alınır)"
            ),
            QuizQuestion(
                id = 3,
                question = "Sürtünme kuvveti hangi formülle hesaplanır?",
                type = QuestionType.TEST,
                options = listOf("f = μN", "f = μm", "f = N/μ", "f = mg"),
                correctAnswerIndex = 0,
                explanation = "Sürtünme kuvveti = sürtünme katsayısı × normal kuvvet, yani f = μN"
            ),
            QuizQuestion(
                id = 4,
                question = "Düzgün hızlı harekette ivme kaçtır?",
                type = QuestionType.TEST,
                options = listOf("0", "sabit", "artıyor", "azalıyor"),
                correctAnswerIndex = 0,
                explanation = "Düzgün hızlı harekette hız sabittir, dolayısıyla ivme sıfırdır."
            ),
            QuizQuestion(
                id = 5,
                question = "Atalet nedir?",
                type = QuestionType.TEST,
                options = listOf("Cismin durumunu koruma eğilimi", "Hareket kabiliyeti", "Kuvvet uygulama", "Hız kazanma"),
                correctAnswerIndex = 0,
                explanation = "Atalet, cisimlerin mevcut hareket durumlarını koruma eğilimidir (Newton'un birinci yasası)"
            )
        )
        QuestionType.OPEN_ENDED -> listOf(
            QuizQuestion(
                id = 6,
                question = "Newton'un üç hareket yasasını açıklayın.",
                type = QuestionType.OPEN_ENDED,
                correctAnswer = "1. Atalet yasası: Net kuvvet sıfırsa cisim durumunu korur. 2. F=ma: Net kuvvet kütle ve ivmenin çarpımıdır. 3. Etki-tepki: Her etkiye eşit ve zıt tepki vardır.",
                explanation = "Bu yasalar klasik mekaniğin temelini oluşturur ve günlük hayattaki tüm hareketleri açıklar."
            ),
            QuizQuestion(
                id = 7,
                question = "Sürtünme kuvvetinin faydaları ve zararları nelerdir?",
                type = QuestionType.OPEN_ENDED,
                correctAnswer = "Faydalar: Yürüme, araç fren yapma, cisim tutma. Zararlar: Enerji kaybı, aşınma, ısı üretimi.",
                explanation = "Sürtünme olmadan hareket edemez ama aynı zamanda enerji kaybına da neden oluruz."
            ),
            QuizQuestion(
                id = 8,
                question = "Serbest düşme hareketi nedir ve hangi koşullarda gerçekleşir?",
                type = QuestionType.OPEN_ENDED,
                correctAnswer = "Sadece yerçekimi kuvvetinin etkisinde olan hareket. Hava direnci ihmal edilir ve ivme g=9.8 m/s² sabit olur.",
                explanation = "Vakumda tüm cisimler aynı ivmeyle düşer, kütleleri farklı olsa bile."
            ),
            QuizQuestion(
                id = 9,
                question = "Momentum korunumu yasasını açıklayın.",
                type = QuestionType.OPEN_ENDED,
                correctAnswer = "Kapalı bir sistemde dış kuvvet yoksa toplam momentum korunur. m₁v₁ + m₂v₂ = sabit",
                explanation = "Çarpışmalarda ve patlamalarda toplam momentum değişmez, bu yasadan yararlanılır."
            ),
            QuizQuestion(
                id = 10,
                question = "Enerji korunumu yasası nedir?",
                type = QuestionType.OPEN_ENDED,
                correctAnswer = "Enerji ne yaratılır ne yok edilir, sadece bir türden diğerine dönüşür. Toplam enerji sabit kalır.",
                explanation = "Kinetik, potansiyel, ısı, elektrik gibi enerji türleri birbirine dönüşebilir ama toplam miktar korunur."
            )
        )
    }
}

private fun getDefaultQuizQuestions(subject: String, chapter: String, type: QuestionType): List<QuizQuestion> {
    return when (type) {
        QuestionType.TEST -> listOf(
            QuizQuestion(
                id = 1,
                question = "$chapter konusunda hangi kavram en önemlidir?",
                type = QuestionType.TEST,
                options = listOf("Temel kavramlar", "Formüller", "Uygulamalar", "Örnekler"),
                correctAnswerIndex = 0,
                explanation = "Her konuda temel kavramların anlaşılması en önemli adımdır."
            ),
            QuizQuestion(
                id = 2,
                question = "$subject dersinde $chapter konusu neden önemlidir?",
                type = QuestionType.TEST,
                options = listOf("Diğer konuların temelini oluşturur", "Sınavda çıkar", "Kolay", "Zor"),
                correctAnswerIndex = 0,
                explanation = "Her konu, sonraki konuların anlaşılması için temel oluşturur."
            )
        )
        QuestionType.OPEN_ENDED -> listOf(
            QuizQuestion(
                id = 3,
                question = "$chapter konusunun ana hatlarını açıklayın.",
                type = QuestionType.OPEN_ENDED,
                correctAnswer = "Bu konu temel kavramları ve uygulamaları içerir.",
                explanation = "Her konunun kendine özgü temel kavramları ve pratik uygulamaları vardır."
            ),
            QuizQuestion(
                id = 4,
                question = "$chapter konusunda öğrendiğiniz en önemli bilgi nedir?",
                type = QuestionType.OPEN_ENDED,
                correctAnswer = "Konunun temel prensipleri ve uygulama alanları önemlidir.",
                explanation = "Temel prensipleri anlamak, konuyu bütün olarak kavramaya yardımcı olur."
            )
        )
    }
}

// Placeholder functions for other subjects
private fun getChemistryQuizQuestions(chapter: String, type: QuestionType) = getDefaultQuizQuestions("Kimya", chapter, type)
private fun getBiologyQuizQuestions(chapter: String, type: QuestionType) = getDefaultQuizQuestions("Biyoloji", chapter, type)
private fun getHistoryQuizQuestions(chapter: String, type: QuestionType) = getDefaultQuizQuestions("Tarih", chapter, type)
private fun getGeographyQuizQuestions(chapter: String, type: QuestionType) = getDefaultQuizQuestions("Coğrafya", chapter, type)
private fun getTurkishQuizQuestions(chapter: String, type: QuestionType) = getDefaultQuizQuestions("Türk Dili ve Edebiyatı", chapter, type)
private fun getPhilosophyQuizQuestions(chapter: String, type: QuestionType) = getDefaultQuizQuestions("Felsefe", chapter, type)
private fun getReligionQuizQuestions(chapter: String, type: QuestionType) = getDefaultQuizQuestions("Din Kültürü ve Ahlak Bilgisi", chapter, type)