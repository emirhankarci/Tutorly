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

data class GradeSubjectKey(
    val grade: Int,
    val subject: String
)

// Grade 12

// Grade 11
val grade11MathChapters = listOf(
    ChapterInfo("Fonksiyonlar", "Doğrusal ve ikinci dereceden fonksiyonlar", 8, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Türev", "Türev kavramı ve temel kuralları", 10, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("İntegral", "Belirsiz integral ve temel uygulamalar", 7, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Limit ve Süreklilik", "Limit kavramı ve süreklilik", 6, Color(0xFF1976D2), Color(0xFF1976D2))
)

val grade11TurkishChapters = listOf(
    ChapterInfo("Tanzimat Edebiyatı", "Türk edebiyatında yenileşme dönemi", 7, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Servet-i Fünun", "Sanat için sanat anlayışı", 6, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Millî Edebiyat", "Ömer Seyfettin ve Ziya Gökalp", 5, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Cumhuriyet Edebiyatı", "Modern Türk şiiri ve tiyatrosu", 8, Color(0xFF4CAF50), Color(0xFF4CAF50))
)

val grade11PhysicsChapters = listOf(
    ChapterInfo("Çembersel Hareket", "Merkezcil kuvvet ve açısal hız", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Elektrik Akımı", "Ohm yasası ve elektrik devreleri", 10, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Manyetik Alan", "Manyetik kuvvet ve indüksiyon", 8, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("İtki ve Momentum", "Momentum korunumu yasası", 5, Color(0xFFE91E63), Color(0xFFE91E63))
)

val grade11ChemistryChapters = listOf(
    ChapterInfo("Modern Atom Teorisi", "Kuantum sayıları ve orbitaller", 8, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Periyodik Özellikler", "Element periyodikliği", 6, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Kimyasal Kinetik", "Tepkime hızı ve kataliz", 7, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Kimyasal Denge", "Denge sabiti ve Le Chatelier ilkesi", 9, Color(0xFFFF5722), Color(0xFFFF5722))
)

// Grade 10
val grade10ChemistryChapters = listOf(
    ChapterInfo("Asit ve Bazlar", "pH kavramı ve nötrleşme", 7, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Kimyasal Hesaplamalar", "Mol kavramı ve stokiyometri", 9, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Gazlar", "Gaz yasaları ve davranışları", 6, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Çözeltiler", "Derişim ve çözünürlük", 5, Color(0xFFFF5722), Color(0xFFFF5722))
)

val grade10MathChapters = listOf(
    ChapterInfo("İkinci Dereceden Denklemler", "Parabol ve köklerin özellikleri", 7, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Trigonometri", "Sinüs, kosinüs ve tanjant kavramları", 9, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Analitik Geometri", "Koordinat sistemi ve doğru denklemi", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Olasılık", "Basit olasılık hesaplamaları", 5, Color(0xFF1976D2), Color(0xFF1976D2))
)

val grade10TurkishChapters = listOf(
    ChapterInfo("Divan Edebiyatı", "Klasik Türk edebiyatı dönemi", 6, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Halk Edebiyatı", "Anonim halk edebiyatı türleri", 5, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Dilbilgisi İleri", "Fiil çekimi ve söz dizimi", 8, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Yazılı Anlatım", "Deneme ve makale yazımı", 4, Color(0xFF4CAF50), Color(0xFF4CAF50))
)

val grade10PhysicsChapters = listOf(
    ChapterInfo("Elektrik ve Manyetizma", "Elektrik yükü ve elektrik alanı", 9, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Basınç ve Kaldırma Kuvveti", "Sıvı ve gazlarda basınç", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Dalgalar", "Ses dalgaları ve özellikleri", 7, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Optik", "Işığın kırılması ve mercekler", 8, Color(0xFFE91E63), Color(0xFFE91E63))
)

// Grade 9
val grade9MathChapters = listOf(
    ChapterInfo("Sayilar", "Doğal sayilar, tam sayilar ve rasyonel sayilar", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Nicelikler ve Degisimler", "Cebirsel ifadeler, denklemler ve esitsizlikler", 8, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Geometrik Sekiller", "Duzlemde ve uzayda geometrik sekillerin ozellikleri", 7, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Eslik ve Benzerlik", "Sekillerde eslik ve benzerlik iliskileri", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Algoritma ve Bilisim", "Problem cozme algoritmaları ve mantiksal düsünme", 5, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Istatistiksel Arastirma Suresi", "Veri toplama, duzenleme ve analiz yontemleri", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Veriden Olasiliga", "Olasilik kavrami ve hesaplama yontemleri", 7, Color(0xFF1976D2), Color(0xFF1976D2))
)

val grade9TurkishChapters = listOf(
    ChapterInfo("Sozun Inceligi", "Dilin guzelligi ve etkili konusma sanati", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Anlam Arayisi", "Metinlerde anlam bulma ve yorumlama becerileri", 7, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Anlamin Yapi Taslari", "Kelime türleri ve cümle yapısının incelenmesi", 8, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Dilin Zenginligi", "Turkcenin soz varligi ve anlatim olanaklari", 6, Color(0xFFE91E63), Color(0xFFE91E63))
)

val grade9PhysicsChapters = listOf(
    ChapterInfo("Fizik Bilimi ve Kariyer Kesfi", "Fizik biliminin doğası ve fizik alanındaki meslek dalları", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Kuvvet ve Hareket", "Kuvvetlerin etkileri ve cisimlerin hareket yasalarının incelenmesi", 8, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Akiskanlar", "Sıvı ve gazların özellikleri ile basınç ve kaldırma kuvveti", 7, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Enerji", "Enerjinin çeşitleri, dönüşümleri ve korunumu yasaları", 6, Color(0xFFE91E63), Color(0xFFE91E63))
)

val grade9ChemistryChapters = listOf(
    ChapterInfo("Etkilesim", "Atomlar arası etkileşimler ve kimyasal bağların oluşumu", 7, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Cesitlilik", "Maddenin farklı halleri ve karışımların özellikleri", 6, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Surdurulebilirlik", "Doğal kaynakların korunması ve çevre dostu kimya uygulamaları", 5, Color(0xFFFF5722), Color(0xFFFF5722)),
)

val grade9BiologyChapters = listOf(
    ChapterInfo("Yasam", "Canlılık belirtileri ve yaşamın temel özelliklerinin incelenmesi", 7, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Organizasyon", "Hücre yapısı ve canlılardaki organizasyon düzeylerinin öğrenilmesi", 6, Color(0xFFFF5722), Color(0xFFFF5722)),
)

val grade9GeographyChapters = listOf(
    ChapterInfo("Cografyanin Dogasi", "Coğrafya biliminin tanımı, kapsamı ve araştırma yöntemleri", 7, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Mekansal Bilgi Teknolojileri", "Harita, GPS ve coğrafi bilgi sistemlerinin kullanımı", 6, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Dogal Sistemler ve Surecler", "İklim, topografya ve doğal çevrenin özellikleri", 6, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Beseri Sistemler ve Surecler", "Nüfus, yerleşme ve toplumsal yapıların coğrafi dağılımı", 6, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Ekonomik Faaliyetler ve Etkileri", "Tarım, sanayi ve ticaretin mekânsal etkileri", 6, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Afetler ve Surdurulebilir Cevre", "Doğal afetler ve çevresel sürdürülebilirlik ilkeleri", 6, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Bolgeler Ulkeler ve Kuresel Baglantilar", "Dünya coğrafyası ve küresel etkileşimler", 6, Color(0xFFFF5722), Color(0xFFFF5722)),
)

val grade9ReligionChapters = listOf(
    ChapterInfo("Allah Insan Iliskisi", "Allah ile insan arasındaki iliski ve iletisimin temelleri", 5, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("İslam'da Inanc Esaslari", "İslam dininin alti temel inanc esasinin ogretilmesi", 6, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("İslam'da Ibadetler", "İslam dinindeki bes temel ibadet ve uygulamalari", 8, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("İslam'da Ahlak Ilkeleri", "İslami degerler ve ahlaki davranis kurallari", 7, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Kur'an'a Gore Hz. Muhammed", "Kur'an'da Hz. Muhammed'in ornek kisiliği ve yasami", 6, Color(0xFF4CAF50), Color(0xFF4CAF50))
)

val grade9HistoryChapters = listOf(
    ChapterInfo("Gecmişin Insa Surecinde Tarih", "Tarih biliminin yontemleri ve tarihsel düsünce", 5, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("Eski Cag Medeniyetleri", "Ilk medeniyetler ve antik dönem toplumlari", 8, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("Orta Cag Medeniyetleri", "Orta Cag dönemindeki İslam ve Bati medeniyetleri", 7, Color(0xFF795548), Color(0xFF795548))
)



// Grade 8
val grade8MathChapters = listOf(
    ChapterInfo("Üslü İfadeler", "Üs kavramı ve özellikleri", 5, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Kareköklü İfadeler", "Karekök kavramı ve hesaplama", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Doğrusal Denklemler", "Birinci dereceden denklemler", 8, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Geometrik Cisimler", "Prizma, piramit ve hesaplamalar", 5, Color(0xFF1976D2), Color(0xFF1976D2))
)

val grade8TurkishChapters = listOf(
    ChapterInfo("Metin İnceleme", "Metin çözümleme teknikleri", 6, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Destan ve Destanlar", "Türk destanları", 5, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Dil Yapısı", "Ses bilgisi ve kelime yapısı", 7, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Yaratıcı Yazma", "Yaratıcı metin yazma", 4, Color(0xFF4CAF50), Color(0xFF4CAF50))
)

val grade8PhysicsChapters = listOf(
    ChapterInfo("Basınç", "Katı, sıvı ve gaz basıncı", 7, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Işık ve Görme", "Görme kusurları", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Maddenin Yapısı", "Atomlar ve moleküller", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Enerji Dönüşümleri", "Enerji türleri ve dönüşümü", 8, Color(0xFFE91E63), Color(0xFFE91E63))
)

val grade8ChemistryChapters = listOf(
    ChapterInfo("Maddenin Yapısı", "Atom modelleri", 6, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Periyodik Sistem", "Element özellikleri", 7, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Kimyasal Bağlanma", "Temel bağ türleri", 5, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Asit ve Bazlar", "pH kavramı", 6, Color(0xFFFF5722), Color(0xFFFF5722))
)

// Grade 7
val grade7MathChapters = listOf(
    ChapterInfo("Tam Sayılar", "Negatif sayılar ve işlemler", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Rasyonel Sayılar", "Kesir ve ondalık sayılar", 7, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Cebirsel İfadeler", "Değişken ve denklem kavramı", 5, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Geometri ve Ölçme", "Açı ölçme ve çizim", 6, Color(0xFF1976D2), Color(0xFF1976D2))
)

val grade7TurkishChapters = listOf(
    ChapterInfo("Dil ve Anlatım", "Anlatım biçimleri ve türleri", 7, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Dil Bilgisi", "Cümle ögeleri ve türleri", 6, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Edebiyat", "Türk edebiyatına giriş", 5, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Yazılı Anlatım", "Paragraf ve kompozisyon", 4, Color(0xFF4CAF50), Color(0xFF4CAF50))
)

val grade7PhysicsChapters = listOf(
    ChapterInfo("Kuvvet ve Enerji", "İş ve enerji kavramları", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Işık", "Aynalar ve mercekler", 7, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Elektrik Enerjisi", "Elektrik tüketimi", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Saf Madde ve Karışımlar", "Madde türleri", 4, Color(0xFFE91E63), Color(0xFFE91E63))
)

val grade7ChemistryChapters = listOf(
    ChapterInfo("Saf Maddeler", "Element ve bileşik", 5, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Atom ve Periyodik Sistem", "Temel atom yapısı", 6, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Moleküller ve Bileşikler", "Kimyasal formüller", 7, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Fiziksel ve Kimyasal Olaylar", "Tepkime türleri", 4, Color(0xFFFF5722), Color(0xFFFF5722))
)

// Grade 6
val grade6MathChapters = listOf(
    ChapterInfo("Kesirler", "Kesir kavramı ve işlemleri", 7, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Ondalık Gösterim", "Ondalık sayılar ve işlemler", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Çevre ve Alan", "Geometrik şekillerin ölçümü", 5, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Veri İşleme", "Tablo ve grafik okuma", 4, Color(0xFF1976D2), Color(0xFF1976D2))
)

val grade6TurkishChapters = listOf(
    ChapterInfo("Metin Türleri", "Hikaye, şiir ve bilgilendirici metinler", 6, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Yazım ve Noktalama", "Doğru yazım kuralları", 5, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Kelime Bilgisi", "Eş anlamlı ve zıt anlamlı kelimeler", 4, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Sözlü İletişim", "Konuşma ve dinleme becerileri", 3, Color(0xFF4CAF50), Color(0xFF4CAF50))
)

val grade6PhysicsChapters = listOf(
    ChapterInfo("Kuvvet ve Hareket", "Sürtünme kuvveti", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Ses ve Özellikleri", "Ses dalgaları", 4, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Işık ve Görme", "Aydınlatma ve gölge", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Elektrik", "Elektrik devresi", 5, Color(0xFFE91E63), Color(0xFFE91E63))
)

val grade6ChemistryChapters = listOf(
    ChapterInfo("Saf Madde ve Karışımlar", "Madde türleri ve ayırma", 5, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Isı ve Sıcaklık", "Hal değişimleri", 4, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Fiziksel ve Kimyasal Değişimler", "Değişim türleri", 6, Color(0xFFFF5722), Color(0xFFFF5722))
)

// Grade 5
val grade5MathChapters = listOf(
    ChapterInfo("Doğal Sayılar", "Sayma, karşılaştırma ve sıralama", 4, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Toplama ve Çıkarma", "Temel işlemler ve problem çözme", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Çarpma ve Bölme", "Çarpım tablosu ve bölme işlemi", 5, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Geometrik Şekiller", "Üçgen, kare, dikdörtgen tanıma", 3, Color(0xFF1976D2), Color(0xFF1976D2))
)

val grade5TurkishChapters = listOf(
    ChapterInfo("Okuma Anlama", "Metin okuma ve anlama", 5, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Yazma Becerileri", "Basit kompozisyon yazma", 4, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Dil Bilgisi", "Kelime türleri temel bilgiler", 6, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Şiir ve Öykü", "Şiir dinleme ve öykü okuma", 3, Color(0xFF4CAF50), Color(0xFF4CAF50))
)

val grade5PhysicsChapters = listOf(
    ChapterInfo("Kuvvet ve Hareket", "Kuvvet kavramı ve etkisi", 4, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Işık ve Ses", "Işık kaynakları ve ses", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Madde ve Değişim", "Maddenin özellikleri", 3, Color(0xFFE91E63), Color(0xFFE91E63))
)

val grade5ChemistryChapters = listOf(
    ChapterInfo("Madde ve Değişim", "Maddenin özellikleri", 4, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Kuvvet ve Hareket", "Basit kimyasal değişimler", 3, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Işık ve Ses", "Yanma olayı", 3, Color(0xFFFF5722), Color(0xFFFF5722))
)

// Chapter mapping by grade and subject
val chaptersByGradeAndSubject = mapOf(
    // Mathematics
    GradeSubjectKey(5, "Matematik") to grade5MathChapters,
    GradeSubjectKey(6, "Matematik") to grade6MathChapters,
    GradeSubjectKey(7, "Matematik") to grade7MathChapters,
    GradeSubjectKey(8, "Matematik") to grade8MathChapters,
    GradeSubjectKey(10, "Matematik") to grade10MathChapters,
    GradeSubjectKey(11, "Matematik") to grade11MathChapters,

    // Turkish
    GradeSubjectKey(5, "Türkçe") to grade5TurkishChapters,
    GradeSubjectKey(6, "Türkçe") to grade6TurkishChapters,
    GradeSubjectKey(7, "Türkçe") to grade7TurkishChapters,
    GradeSubjectKey(8, "Türkçe") to grade8TurkishChapters,
    GradeSubjectKey(10, "Türkçe") to grade10TurkishChapters,
    GradeSubjectKey(11, "Türkçe") to grade11TurkishChapters,

    // Physics
    GradeSubjectKey(5, "Fizik") to grade5PhysicsChapters,
    GradeSubjectKey(6, "Fizik") to grade6PhysicsChapters,
    GradeSubjectKey(7, "Fizik") to grade7PhysicsChapters,
    GradeSubjectKey(10, "Fizik") to grade10PhysicsChapters,
    GradeSubjectKey(11, "Fizik") to grade11PhysicsChapters,

    // Chemistry
    GradeSubjectKey(5, "Kimya") to grade5ChemistryChapters,
    GradeSubjectKey(6, "Kimya") to grade6ChemistryChapters,
    GradeSubjectKey(7, "Kimya") to grade7ChemistryChapters,
    GradeSubjectKey(8, "Kimya") to grade8ChemistryChapters,
    GradeSubjectKey(10, "Kimya") to grade10ChemistryChapters,
    GradeSubjectKey(11, "Kimya") to grade11ChemistryChapters,

    // 9th Grade
    // Chemistry
    GradeSubjectKey(9, "Kimya") to grade9ChemistryChapters,
    // Physics
    GradeSubjectKey(9, "Fizik") to grade9PhysicsChapters,
    // Turkish
    GradeSubjectKey(9, "Türk Dili ve Edebiyati") to grade9TurkishChapters,
    // Mathematics
    GradeSubjectKey(9, "Matematik") to grade9MathChapters,
    // Biology
    GradeSubjectKey(9, "Biyoloji") to grade9BiologyChapters,
    // Geography
    GradeSubjectKey(9, "Coğrafya") to grade9GeographyChapters,
    // Religion
    GradeSubjectKey(9, "Din Kültürü ve Ahlak Bilgisi") to grade9ReligionChapters,
    // History
    GradeSubjectKey(9, "Tarih") to grade9HistoryChapters,

)

// Default chapters for backward compatibility
val chapters = grade9MathChapters







































