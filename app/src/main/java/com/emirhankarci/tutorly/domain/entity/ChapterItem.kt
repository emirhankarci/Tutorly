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

// 12. Sınıf Coğrafya
val grade12GeographyChapters = listOf(
    ChapterInfo("Doğal Sistemler", "Yeryüzü şekilleri, iklim ve ekosistemler", 7, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Beşeri Sistemler", "Nüfus, yerleşme ve ekonomik faaliyetler", 6, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Küresel Ortam: Bölgeler ve Ülkeler", "Küresel bölgeler ve ekonomik ilişkiler", 5, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Çevre ve Toplum", "İnsan-çevre etkileşimi ve sürdürülebilirlik", 4, Color(0xFFFFC107), Color(0xFFFFC107))
)

// 12. Sınıf Türk Dili ve Edebiyatı
val grade12TurkishChapters = listOf(
    ChapterInfo("Giriş", "Türk edebiyatına genel bakış", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Hikaye", "Modern hikaye türü ve örnekleri", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Şiir", "Şiir türleri, biçim ve içerik analizi", 7, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Roman", "Roman türü ve temaları", 8, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Tiyatro", "Dramatik metinler ve tiyatro sanatının özellikleri", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Deneme", "Deneme türünün özellikleri ve örnekler", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Söylev", "Söylev türü ve ünlü örnekler", 4, Color(0xFFE91E63), Color(0xFFE91E63))
)

// 12. Sınıf Fizik
val grade12PhysicsChapters = listOf(
    ChapterInfo("Çembersel Hareket", "Dönme hareketi ve merkezcil kuvvet", 7, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Basit Harmonik Hareket", "Salınım hareketi ve denklemleri", 6, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Dalga Mekaniği", "Mekanik ve elektromanyetik dalgalar", 8, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Atom Fiziğine Giriş ve Radyoaktivite", "Atom modeli ve radyoaktif bozunma", 7, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Modern Fizik", "Kuantum fiziği ve temel ilkeler", 8, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Modern Fiziğin Uygulamaları", "Yarı iletkenler, lazerler ve modern cihazlar", 6, Color(0xFFFF5722), Color(0xFFFF5722))
)

// 12. Sınıf Kimya
val grade12ChemistryChapters = listOf(
    ChapterInfo("Kimya ve Elektrik", "Elektrokimya ve pil sistemleri", 7, Color(0xFF00BCD4), Color(0xFF00BCD4)),
    ChapterInfo("Karbon Kimyasına Giriş", "Organik kimyanın temelleri", 6, Color(0xFF00BCD4), Color(0xFF00BCD4)),
    ChapterInfo("Organik Bileşikler", "Organik bileşiklerin sınıflandırılması ve özellikleri", 8, Color(0xFF00BCD4), Color(0xFF00BCD4)),
    ChapterInfo("Enerji Kaynakları", "Fosil ve alternatif enerji kaynakları", 5, Color(0xFF00BCD4), Color(0xFF00BCD4))
)

// 12. Sınıf Biyoloji
val grade12BiologyChapters = listOf(
    ChapterInfo("Genden Proteine", "DNA, RNA ve protein sentezi", 8, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Canlılarda Enerji Dönüşümü", "Fotosentez, solunum ve enerji döngüsü", 7, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Bitki Biyolojisi", "Bitkilerin yapısı ve fizyolojisi", 6, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Canlılar ve Çevre", "Ekolojik ilişkiler ve çevre sorunları", 5, Color(0xFF4CAF50), Color(0xFF4CAF50))
)

// 12. Sınıf Din Kültürü
val grade12ReligionChapters = listOf(
    ChapterInfo("İslam ve Bilim", "İslam'da bilimin yeri", 5, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("Anadolu'da İslam", "Anadolu'da İslam'ın yayılması ve etkileri", 5, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("Tasavvufi Düşünce", "Tasavvuf felsefesi ve önemli mutasavvıflar", 6, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("Güncel Dini Meseleler", "Modern çağda dini konular", 5, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("Hint ve Çin Dinleri", "Doğu dinleri ve inanç sistemleri", 5, Color(0xFFFF9800), Color(0xFFFF9800))
)

// 12. Sınıf Matematik
val grade12MathChapters = listOf(
    ChapterInfo("Sayılar ve Cebir", "Karmaşık sayılar ve cebirsel ifadeler", 8, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Geometri", "Temel geometri kavramları", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Sayılar ve Cebir 2", "İleri seviye cebirsel ifadeler", 7, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Geometri 2", "Katı cisimler ve analitik geometri", 7, Color(0xFF1976D2), Color(0xFF1976D2))
)

// 12. Sınıf Çağdaş Türk ve Dünya Tarihi
val grade12HistoryChapters = listOf(
    ChapterInfo("İki Küresel Savaş Arasında Dünya", "1919-1939 dönemi dünya siyaseti", 6, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("İkinci Dünya Savaşı", "Savaşın nedenleri, gelişimi ve sonuçları", 7, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("Soğuk Savaş", "ABD-SSCB rekabeti ve dünya politikası", 8, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("Yumuşama Dönemi ve Sonrası", "Soğuk Savaş sonrası dünya düzeni", 6, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("Küreselleşen Dünya", "Küreselleşme süreci ve etkileri", 5, Color(0xFF795548), Color(0xFF795548))
)










// Grade 11
val grade11MathChapters = listOf(
    ChapterInfo("Fonksiyonlar", "Doğrusal ve ikinci dereceden fonksiyonlar", 8, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Türev", "Türev kavramı ve temel kuralları", 10, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("İntegral", "Belirsiz integral ve temel uygulamalar", 7, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Limit ve Süreklilik", "Limit kavramı ve süreklilik", 6, Color(0xFF1976D2), Color(0xFF1976D2))
)

val grade11TurkishChapters = listOf(
    ChapterInfo("Hikaye", "Hikaye türü ve özellikleri", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Şiir", "Şiir türleri ve şiir dili", 7, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Masal ve Fabl", "Masal ve fabl türlerinin özellikleri", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Gezi Yazısı", "Gezi yazısı türü ve örnekleri", 4, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Roman", "Roman türü ve yapısı", 8, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Tiyatro", "Tiyatro türleri ve temel kavramlar", 6, Color(0xFFE91E63), Color(0xFFE91E63))
)

val grade11PhysicsChapters = listOf(
    ChapterInfo("Kuvvet ve Hareket", "Newton yasaları ve hareket türleri", 8, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Elektrik ve Manyetizma", "Elektrik alan, potansiyel ve manyetik alan", 9, Color(0xFFFF5722), Color(0xFFFF5722))
)

val grade11ChemistryChapters = listOf(
    ChapterInfo("Modern Atom Teorisi", "Atomun yapısı ve kuantum sayıları", 8, Color(0xFF00BCD4), Color(0xFF00BCD4)),
    ChapterInfo("Gazlar", "Gaz yasaları ve ideal gaz denklemi", 7, Color(0xFF00BCD4), Color(0xFF00BCD4)),
    ChapterInfo("Sıvı Çözeltiler ve Çözünürlük", "Çözelti türleri ve çözünürlük faktörleri", 6, Color(0xFF00BCD4), Color(0xFF00BCD4)),
    ChapterInfo("Kimyasal Tepkimelerde Enerji", "Endotermik ve ekzotermik tepkimeler", 8, Color(0xFF00BCD4), Color(0xFF00BCD4)),
    ChapterInfo("Kimyasal Tepkimelerde Hız", "Tepkime hızını etkileyen faktörler", 6, Color(0xFF00BCD4), Color(0xFF00BCD4)),
)
val grade11ReligionChapters = listOf(
    ChapterInfo("Dünya ve Ahiret", "İslam inancında dünya ve ahiret anlayışı", 5, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("Kur'an'a Göre Hz. Muhammed", "Hz. Muhammed'in örnek kişiliği", 6, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("Kur'an'da Bazı Kavramlar", "İslam'daki temel kavramlar", 5, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("İnanç ile İlgili Meseleler", "İnanç esasları ve modern meseleler", 5, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("Yahudilik ve Hristiyanlık", "Semavi dinlerin temel özellikleri", 6, Color(0xFFFF9800), Color(0xFFFF9800))
)

// Tarih
val grade11HistoryChapters = listOf(
    ChapterInfo("Değişen Dünya Dengeleri ve Osmanlı", "Osmanlı'nın 18. yüzyıldaki siyasi durumu", 6, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("Değişim Çağında Avrupa ve Osmanlı", "Avrupa'daki dönüşümler ve Osmanlı üzerindeki etkiler", 5, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("Uluslararası İlişkilerde Denge Stratejisi", "Osmanlı'nın denge politikası", 5, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("Devrimler Çağında Değişen Devlet-Toplum İlişkileri", "Sanayi ve Fransız Devrimi'nin etkileri", 7, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("Sermaye ve Emek", "Kapitalizm, işçi sınıfı ve toplumsal değişim", 6, Color(0xFF795548), Color(0xFF795548))
)

val grade11GeographyChapters = listOf(
    ChapterInfo("Doğal Sistemler", "Yeryüzü şekilleri, iklim ve ekosistemler", 7, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Beşeri Sistemler", "Nüfus, yerleşme ve ekonomik faaliyetler", 6, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Küresel Ortam", "Küresel sorunlar ve çevre politikaları", 5, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Çevre ve Toplum", "İnsan ve çevre ilişkileri", 4, Color(0xFFFFC107), Color(0xFFFFC107))
)




// Grade 10
val grade10BiologyChapters = listOf(
    ChapterInfo("Enerji", "Hücrede enerji dönüşümleri ve metabolizma", 7, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Ekoloji", "Canlılar ve çevre arasındaki ilişkiler", 8, Color(0xFF4CAF50), Color(0xFF4CAF50))
)

val grade10GeographyChapters = listOf(
    ChapterInfo("Coğrafyanın Doğası", "Coğrafya biliminin tanımı, kapsamı ve araştırma yöntemleri", 5, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Mekânsal Bilgi Teknolojileri", "Harita, GPS ve coğrafi bilgi sistemlerinin kullanımı", 6, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Doğal Sistemler ve Süreçler", "İklim, topografya ve doğal çevrenin özellikleri", 8, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Beşeri Sistemler ve Süreçler", "Nüfus, yerleşme ve toplumsal yapıların coğrafi dağılımı", 7, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Ekonomik Faaliyetler ve Etkileri", "Tarım, sanayi ve ticaretin mekânsal etkileri", 7, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Afetler ve Sürdürülebilir Çevre", "Doğal afetler ve çevresel sürdürülebilirlik ilkeleri", 6, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Bölgeler, Ülkeler ve Küresel Bağlantılar", "Dünya coğrafyası ve küresel etkileşimler", 8, Color(0xFFFFC107), Color(0xFFFFC107))
)

val grade11BiologyChapters = listOf(
    ChapterInfo("İnsan Fizyolojisi", "Sinir, duyu ve dolaşım sistemleri", 8, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Komünite ve Popülasyon Ekolojisi", "Ekolojik denge ve türler arası ilişkiler", 6, Color(0xFF4CAF50), Color(0xFF4CAF50))
)



val grade10PhilosophyChapters = listOf(
    ChapterInfo("Felsefenin Doğuşu", "Felsefenin tarihi ve temel kavramları", 5, Color(0xFF9C27B0), Color(0xFF9C27B0)),
    ChapterInfo("Felsefe Mantık ve Argümantasyon", "Mantıksal düşünme ve doğru akıl yürütme yöntemleri", 7, Color(0xFF9C27B0), Color(0xFF9C27B0)),
    ChapterInfo("Varlık Felsefesi", "Var olan hakkında felsefi sorgulamalar", 6, Color(0xFF9C27B0), Color(0xFF9C27B0)),
    ChapterInfo("Bilgi Felsefesi", "Bilginin kaynağı, doğası ve sınırları", 6, Color(0xFF9C27B0), Color(0xFF9C27B0)),
    ChapterInfo("Ahlak Felsefesi", "Ahlaki değerler ve doğru davranış sorgulaması", 7, Color(0xFF9C27B0), Color(0xFF9C27B0)),
    ChapterInfo("Estetik ve Sanat Felsefesi", "Güzellik ve sanat üzerine felsefi düşünceler", 5, Color(0xFF9C27B0), Color(0xFF9C27B0)),
    ChapterInfo("Siyaset Felsefesi", "Devlet, adalet ve toplum felsefesi", 6, Color(0xFF9C27B0), Color(0xFF9C27B0)),
    ChapterInfo("Din Felsefesi", "Dini inançların felsefi temelleri", 5, Color(0xFF9C27B0), Color(0xFF9C27B0)),
    ChapterInfo("Bilim Felsefesi", "Bilimsel bilginin doğası ve yöntemleri", 6, Color(0xFF9C27B0), Color(0xFF9C27B0))
)

val grade10PhysicsChapters = listOf(
    ChapterInfo("Kuvvet ve Hareket", "Kuvvetlerin etkileri ve hareket yasalarının incelenmesi", 8, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Enerji", "Enerjinin Çeşitleri, Dönüşümleri ve Korunumu Yasaları", 7, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Elektrik", "Elektrik yükleri, akım ve devreler", 8, Color(0xFFFF5722), Color(0xFFFF5722)),
    ChapterInfo("Dalgalar", "Dalga hareketi ve özellikleri", 7, Color(0xFFFF5722), Color(0xFFFF5722))
)

val grade10ChemistryChapters = listOf(
    ChapterInfo("Etkileşim", "Atomlar arası etkileşimler ve kimyasal bağların oluşumu", 7, Color(0xFF00BCD4), Color(0xFF00BCD4)),
    ChapterInfo("Çeşitlilik", "Maddenin farklı halleri ve karışımların özellikleri", 8, Color(0xFF00BCD4), Color(0xFF00BCD4)),
    ChapterInfo("Sürdürülebilirlik", "Doğal kaynakların korunması ve çevre dostu kimya uygulamaları", 6, Color(0xFF00BCD4), Color(0xFF00BCD4))
)

val grade10MathChapters = listOf(
    ChapterInfo("Geometrik Şekiller", "Düzlemde ve uzayda geometrik şekillerin özellikleri", 7, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("İstatistiksel Araştırma Süreci", "Veri toplama, düzenleme ve analiz yöntemleri", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Sayılar", "Rasyonel ve irrasyonel sayılar", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Nicelikler ve Değişimler", "Cebirsel ifadeler, denklemler ve eşitsizlikler", 8, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Sayma, Algoritma ve Bilişim", "Sayma yöntemleri ve problem çözme algoritmaları", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Analitik İnceleme", "Analitik geometri ve koordinat sistemi", 7, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("Veriden Olasılığa", "Olasılık kavramı ve hesaplama yöntemleri", 7, Color(0xFF1976D2), Color(0xFF1976D2))
)

val grade10HistoryChapters = listOf(
    ChapterInfo("Türkistan'dan Türkiye'ye", "Türklerin tarih sahnesine çıkışı ve göç hareketleri", 7, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("Beylikten Devlete Osmanlı", "Osmanlı Devleti'nin kuruluşu ve gelişimi", 8, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("Cihan Devleti Osmanlı", "Osmanlı'nın yükseliş dönemi ve dünya hâkimiyeti", 8, Color(0xFF795548), Color(0xFF795548))
)

val grade10TurkishChapters = listOf(
    ChapterInfo("Sözün Ezgisi", "Şiir ve edebiyatta ritim ve ahenk", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Kelimelerin Ritmi", "Sözcüklerin ses ve anlam uyumu", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Dünden Bugüne", "Türk edebiyatının tarihsel gelişimi", 7, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("Nesillerin Mirası", "Edebî eserler ve kültürel aktarım", 7, Color(0xFFE91E63), Color(0xFFE91E63))
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
    ChapterInfo("Yasam", "Canlılık belirtileri ve yaşamın temel özelliklerinin incelenmesi", 7, Color(0xFF4CAF50), Color(0xFF4CAF50)),
    ChapterInfo("Organizasyon", "Hücre yapısı ve canlılardaki organizasyon düzeylerinin öğrenilmesi", 6, Color(0xFF4CAF50), Color(0xFF4CAF50)),
)

val grade9GeographyChapters = listOf(
    ChapterInfo("Cografyanin Dogasi", "Coğrafya biliminin tanımı, kapsamı ve araştırma yöntemleri", 7, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Mekansal Bilgi Teknolojileri", "Harita, GPS ve coğrafi bilgi sistemlerinin kullanımı", 6, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Dogal Sistemler ve Surecler", "İklim, topografya ve doğal çevrenin özellikleri", 6, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Beseri Sistemler ve Surecler", "Nüfus, yerleşme ve toplumsal yapıların coğrafi dağılımı", 6, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Ekonomik Faaliyetler ve Etkileri", "Tarım, sanayi ve ticaretin mekânsal etkileri", 6, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Afetler ve Surdurulebilir Cevre", "Doğal afetler ve çevresel sürdürülebilirlik ilkeleri", 6, Color(0xFFFFC107), Color(0xFFFFC107)),
    ChapterInfo("Bolgeler Ulkeler ve Kuresel Baglantilar", "Dünya coğrafyası ve küresel etkileşimler", 6, Color(0xFFFFC107), Color(0xFFFFC107)),
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
// 8. Sınıf Din Kültürü
val grade8ReligionChapters = listOf(
    ChapterInfo("kader ve inanç", "İnsanın kaderi ve inanç ilişkisi", 5, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("zekât ve sadaka", "Zekât, sadaka ve paylaşmanın önemi", 5, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("din ve hayat", "İnancın günlük yaşamdaki yeri", 5, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("hz. muhammed'in örnekliği", "Hz. Muhammed'in ahlaki örnekliği", 5, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("kur'an ve özellikleri", "Kur'an-ı Kerim'in temel özellikleri", 5, Color(0xFFFF9800), Color(0xFFFF9800))
)

// 8. Sınıf Türkçe
val grade8TurkishChapters = listOf(
    ChapterInfo("erdemler", "İyi insan olmanın değerleri", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("millî mücadele ve atatürk", "Kurtuluş Savaşı ve Atatürk'ün önemi", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("bilim ve teknoloji", "Bilimin gelişimi ve teknolojiyle ilişkisi", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("birey ve toplum", "Toplum içinde bireyin rolü", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("zaman ve mekân", "Olayların zamanı ve mekânla ilişkisi", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("millî kültürümüz", "Kültürümüzün unsurları ve önemi", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("doğa ve evren", "Doğa, çevre ve insan ilişkisi", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("vatandaşlık", "Sorumluluklar ve toplumsal bilinç", 5, Color(0xFFE91E63), Color(0xFFE91E63))
)

// 8. Sınıf Fen Bilimleri
val grade8ScienceChapters = listOf(
    ChapterInfo("mevsimler ve iklim", "Mevsimlerin oluşumu ve iklim özellikleri", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("dna ve genetik kod", "Kalıtım ve genetik yapı", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("basınç", "Katı, sıvı ve gaz basıncı", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("madde ve endüstri", "Maddenin yapısı ve endüstride kullanımı", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("basit makineler", "Kuvvet ve basit makinelerin kullanımı", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("enerji dönüşümleri ve çevre bilimi", "Enerji türleri ve çevresel etkileri", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("elektrik yükleri ve elektrik enerjisi", "Elektrik akımı ve devre elemanları", 6, Color(0xFF0288D1), Color(0xFF0288D1))
)

// 8. Sınıf İnkılap Tarihi
val grade8HistoryChapters = listOf(
    ChapterInfo("bir kahraman doğuyor", "Mustafa Kemal'in çocukluğu ve ilk yılları", 6, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("millî uyanış", "Kurtuluş Savaşı'nın başlangıcı", 6, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("millî bir destan", "Sakarya ve Büyük Taarruz dönemleri", 6, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("atatürkçülük ve çağdaşlaşan türkiye", "Atatürk ilkeleri ve reformlar", 6, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("demokratikleşme çabaları", "Cumhuriyet dönemi demokratik gelişmeler", 6, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("atatürk dönemleri türk dış politikası", "Atatürk döneminde dış ilişkiler", 6, Color(0xFF795548), Color(0xFF795548)),
    ChapterInfo("atatürk'ün ölümü ve sonrası", "Atatürk'ün vefatı ve Cumhuriyet'in devamı", 6, Color(0xFF795548), Color(0xFF795548))
)

// 8. Sınıf Matematik
val grade8MathChapters = listOf(
    ChapterInfo("çarpanlar ve katlar", "Sayıların çarpanları ve katları", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("karekök ifadeler", "Karekök kavramı ve işlemleri", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("olasılık", "Temel olasılık kavramları", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("doğrusal denklem", "Birinci dereceden denklemler", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("üçgen, eşlik ve benzerlik", "Üçgenlerin özellikleri, eşlik ve benzerlik", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("dönüşüm geometrisi", "Dönme, öteleme, yansıma kavramları", 6, Color(0xFF1976D2), Color(0xFF1976D2))
)


// Grade 7
// 7. Sınıf Sosyal Bilgiler
val grade7SocialChapters = listOf(
    ChapterInfo("birey, toplum ve iletişim", "Toplumda bireyin yeri ve iletişim becerileri", 6, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("osmanlı devleti ve kültür", "Osmanlı Devleti'nin kültürel ve sosyal yapısı", 6, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("ülkemizde yerleşme, nüfus ve göç", "Yerleşme düzeni, nüfus ve göç hareketleri", 6, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("geçmişten günümüze bilim", "Bilimin tarihsel gelişimi ve öncü bilim insanları", 6, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("üretim ve sosyal hayat", "Ekonomik faaliyetler ve sosyal yaşam ilişkisi", 6, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("demokrasi ve vatandaşlık", "Demokratik haklar ve sorumluluk bilinci", 6, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("türkiye ve dünya", "Türkiye'nin küresel konumu ve uluslararası ilişkiler", 6, Color(0xFF009688), Color(0xFF009688))
)

// 7. Sınıf Türkçe
val grade7TurkishChapters = listOf(
    ChapterInfo("kişisel gelişim", "Kendini tanıma, hedef belirleme ve motivasyon", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("millî mücadele ve atatürk", "Kurtuluş Savaşı ve Atatürk'ün liderliği", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("bilim ve teknoloji", "Bilimsel gelişmelerin topluma etkisi", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("erdemler", "Doğruluk, adalet, yardımseverlik gibi değerler", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("millî kültürümüz", "Kültürümüzün öğeleri ve önemi", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("doğa ve evren", "Doğal çevre, insan ve evren ilişkisi", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("sağlık ve spor", "Sağlıklı yaşam ve sporun önemi", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("sanat", "Sanatın yaşamdaki yeri ve önemi", 6, Color(0xFFE91E63), Color(0xFFE91E63))
)

// 7. Sınıf Fen Bilimleri
val grade7ScienceChapters = listOf(
    ChapterInfo("güneş sistemi ve ötesi", "Güneş sistemi, gezegenler ve uzay araştırmaları", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("hücre ve bölünmesi", "Canlıların temel birimi ve hücre bölünmesi", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("kuvvet ve enerji", "Kuvvetin hareket üzerindeki etkisi", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("saf madde ve karışımlar", "Saf maddelerin ve karışımların özellikleri", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("ışığın madde ile etkileşimi", "Işığın yansıması, kırılması ve renk oluşumu", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("canlılarda üreme, büyüme ve gelişme", "Canlıların yaşam döngüsü ve üreme çeşitleri", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("elektrik devreleri", "Elektrik akımı ve devre elemanları", 6, Color(0xFF0288D1), Color(0xFF0288D1))
)

// 7. Sınıf Matematik
val grade7MathChapters = listOf(
    ChapterInfo("tam sayılarda işlemler", "Pozitif ve negatif sayılarla işlemler", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("rasyonel sayılar", "Kesirli sayılar ve işlemleri", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("cebirsel ifadeler ve denklemler", "Cebirsel ifadeler, eşitlik ve denklemler", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("oran ve orantı", "Oran, orantı ve doğru orantı kavramları", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("geometri", "Geometrik şekillerin özellikleri", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("veri analizi", "Veri toplama, tablo ve grafik yorumlama", 6, Color(0xFF1976D2), Color(0xFF1976D2))
)

// 7. Sınıf Din Kültürü
val grade7ReligionChapters = listOf(
    ChapterInfo("melek ve ahiret inancı", "Meleklere ve ahiret gününe inanmanın önemi", 6, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("hac ve kurban", "Hac ibadeti ve kurbanın anlamı", 6, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("ahlaki davranışlar", "İslam ahlakı ve güzel davranışlar", 6, Color(0xFFFF9800), Color(0xFFFF9800)))



// Grade 6
val grade6MathChapters = listOf(
    ChapterInfo("sayılar ve nicelikler", "sayılarla işlemler ve nicelik kavramları", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("istatistiksel araştırma süreci", "veri toplama ve istatistik süreçleri", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("sayılar ve nicelikler 2", "sayılar ve daha karmaşık işlemler", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("veride olasılığa", "olasılık ve veri yorumlama", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("geometrik şekiller", "geometrik şekillerin özellikleri", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("işlemlerle cebirsel düşünme", "algebra ve mantıksal düşünme", 6, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("geometrik nicelikler", "geometrik ölçüler ve alan hesapları", 6, Color(0xFF1976D2), Color(0xFF1976D2))
)

val grade6TurkishChapters = listOf(
    ChapterInfo("dilimizin zenginliği", "türkçenin farklı yönleri ve zenginliği", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("bağımsızlık yolu", "tarihte bağımsızlık ve özgürlük hikayeleri", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("farklı dünyalar", "hayal gücü ve farklı dünyaları keşfetmek", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("iletişim ve sosyal ilişkiler", "etkili iletişim ve ilişkiler", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("bilim ve teknoloji", "bilimsel ve teknolojik gelişmeler", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("lider ruhlar", "liderlik özellikleri ve örnekleri", 6, Color(0xFFE91E63), Color(0xFFE91E63)),
)

val grade6ScienceChapters = listOf(
    ChapterInfo("güneş sistemi ve tutulmalar", "güneş sistemi ve gök olayları", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("kuvvetin etkisinde hareket", "hareket ve kuvvet ilişkisi", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("canlılarda sistemler", "canlıların sistemleri ve işleyişi", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("ışığın yansıması ve renkler", "ışık, yansıma ve renkler", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("maddenin ayırt edici özellikleri", "maddenin özelliklerini tanımak", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("elektriğin iletimi ve direnç", "elektrik akımı ve direnç kavramları", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("sürdürülebilir yaşam ve etkileşim", "çevre ve sürdürülebilir yaşam bilinci", 6, Color(0xFF0288D1), Color(0xFF0288D1)),
)
val grade6SocialChapters = listOf(
    ChapterInfo("birlikte yaşamak", "hayatımızda birlikte yaşamanın önemi", 6, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("evimiz dünya", "dünyayı ortak evimiz olarak görmek", 6, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("ortak mirasımız", "kültürel ve doğal miraslarımızı korumak", 6, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("yaşayan demokrasimiz", "demokrasi ve katılım süreçleri", 6, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("hayatımızdaki ekonomi", "ekonomik faaliyetler ve günlük hayat", 6, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("teknoloji ve sosyal bilimler", "teknolojinin sosyal bilimlerle ilişkisi", 6, Color(0xFF009688), Color(0xFF009688)),
)


// Grade 5
val grade5MathChapters = listOf(
    ChapterInfo("geometrik şekiller", "geometrik şekillerin özellikleri", 5, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("sayılar ve nicelikler", "sayılarla işlemler ve nicelik kavramları", 5, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("geometrik nicelikler", "geometrik ölçüler ve alan hesapları", 5, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("sayılar ve nicelikler 2", "sayılar ve daha karmaşık işlemler", 5, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("istatistiksel araştırma süreci", "veri toplama ve istatistik süreçleri", 5, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("işlemlerle cebirsel düşünme", "algebra ve mantıksal düşünme", 5, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("veriden olasılığa", "olasılık ve veri yorumlama", 5, Color(0xFF1976D2), Color(0xFF1976D2)),
)

val grade5TurkishChapters = listOf(
    ChapterInfo("oyun dünyası", "oyun ve eğlence ile öğrenme", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("ataturku tanımak", "atatürk'ün hayatı ve eserleri", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("duygularımı tanıyorum", "duyguları tanıma ve ifade etme", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("geleneklerimiz", "kültürel geleneklerimizi keşfetmek", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("iletişim ve sosyal ilişkiler", "etkili iletişim ve ilişkiler", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("sağlıklı yaşıyorum", "sağlık ve günlük yaşam alışkanlıkları", 5, Color(0xFFE91E63), Color(0xFFE91E63)),
)

val grade5ScienceChapters = listOf(
    ChapterInfo("gökyüzündeki komşularımız ve biz", "gök cisimleri ve güneş sistemi", 5, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("kuvvet ve kuvvetin ölçülmesi", "kuvvet ve ölçüm yöntemleri", 5, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("sürtünme kuvveti", "sürtünme ve etkileri", 5, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("canlıların yapısına yolculuk", "canlıların yapı ve sistemleri", 5, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("destek ve hareket sistemi", "vücudun destek ve hareket sistemi", 5, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("ışığın dünyası", "ışık ve yansıma olayları", 5, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("maddenin tanecikli yapısı", "maddenin yapı taşları ve özellikleri", 5, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("yaşamamızdaki elektrik", "elektrik ve günlük yaşam", 5, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("sürdürülebilir yaşam ve geri dönüşüm", "çevre ve sürdürülebilir yaşam", 5, Color(0xFF0288D1), Color(0xFF0288D1)),
)

val grade5SocialChapters = listOf(
    ChapterInfo("birlikte yaşamak", "hayatımızda birlikte yaşamanın önemi", 5, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("evimiz dünya", "dünyayı ortak evimiz olarak görmek", 5, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("ortak mirasımız", "kültürel ve doğal miraslarımızı korumak", 5, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("yaşayan demokrasimiz", "demokrasi ve katılım süreçleri", 5, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("hayatımızdaki ekonomi", "ekonomik faaliyetler ve günlük hayat", 5, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("teknoloji ve sosyal bilimler", "teknolojinin sosyal bilimlerle ilişkisi", 5, Color(0xFF009688), Color(0xFF009688)),
)

val grade5ReligionChapters = listOf(
    ChapterInfo("allah inancı", "allah inancı ve temel bilgiler", 5, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("namaz", "namaz ibadetinin önemi ve kuralları", 5, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("kurani kerim", "kuran-ı kerim ve mesajları", 5, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("peygamber kıssaları", "peygamberlerin hayat hikayeleri", 5, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("mimarimizde dini motifler", "dini motifler ve mimari örnekler", 5, Color(0xFFFF9800), Color(0xFFFF9800))
)

// 4. Sınıf Matematik
val grade4MathChapters = listOf(
    ChapterInfo("doğal sayılar", "doğal sayılar ve özellikleri", 4, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("doğal sayılarda işlemler", "doğal sayılarla toplama, çıkarma, çarpma ve bölme", 4, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("doğal sayılarda işlemler 2", "doğal sayılarla daha karmaşık işlemler", 4, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("kesirler", "kesirleri tanıma ve işlemler", 4, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("geometri", "geometrik şekiller ve özellikleri", 4, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("çevre ve alan", "geometrik şekillerin çevre ve alan hesapları", 4, Color(0xFF1976D2), Color(0xFF1976D2))
)

// 4. Sınıf Türkçe
val grade4TurkishChapters = listOf(
    ChapterInfo("çocuk dünyası", "çocuk dünyası ve hayal gücü", 4, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("milli mücadele ve atatürk", "tarihimizde milli mücadele ve atatürk", 4, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("erdemler", "erdemli davranışlar ve değerler", 4, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("milli kültürümüz", "milli kültür ve geleneklerimiz", 4, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("doğa ve evren", "doğa olayları ve evreni keşfetmek", 4, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("sanat", "sanat ve estetik anlayışı", 4, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("birey ve toplum", "birey ve toplum ilişkileri", 4, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("bilim ve teknoloji", "bilimsel gelişmeler ve teknoloji", 4, Color(0xFFE91E63), Color(0xFFE91E63))
)

// 4. Sınıf Din
val grade4ReligionChapters = listOf(
    ChapterInfo("günlük hayattaki dini ifadeler", "günlük hayatta kullanılan dini ifadeler", 4, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("islami tanıyalım", "islamın temel kavramlarını öğrenmek", 4, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("güzel ahlak", "güzel ahlak ve erdemli davranışlar", 4, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("hz. muhammadi tanıyalım", "peygamberimizin hayatı ve öğretileri", 4, Color(0xFFFF9800), Color(0xFFFF9800)),
    ChapterInfo("din ve temizlik", "dinî temizlik ve ibadet hazırlıkları", 4, Color(0xFFFF9800), Color(0xFFFF9800))
)


// 3. Sınıf Hayat Bilgisi
val grade3LifeChapters = listOf(
    ChapterInfo("okulumuzda hayat", "okul hayatını tanımak ve öğrenmek", 3, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("evimizde hayat", "evde günlük yaşam ve sorumluluklar", 3, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("sağlıklı hayat", "sağlıklı yaşam ve alışkanlıklar", 3, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("güvenli hayat", "güvenli davranışlar ve çevre bilinci", 3, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("ülkemizde hayat", "ülkemizde yaşam ve kültürel değerler", 3, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("doğada hayat", "doğadaki yaşam ve çevreyi koruma", 3, Color(0xFF009688), Color(0xFF009688))
)

// 3. Sınıf Fen
val grade3ScienceChapters = listOf(
    ChapterInfo("gezegenimizi tanıyalım", "gezegenler ve güneş sistemi", 3, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("duyu organları ve görevleri", "duyu organları ve işlevleri", 3, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("kuvveti tanıyalım", "kuvvet ve etkilerini keşfetmek", 3, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("maddeyi tanıyalım", "maddenin özellikleri ve halleri", 3, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("çevremizdeki ışık ve sesler", "ışık ve ses olaylarını anlamak", 3, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("canlıların dünyasına yolculuk", "canlıları ve yaşam sistemlerini keşfetmek", 3, Color(0xFF0288D1), Color(0xFF0288D1)),
    ChapterInfo("elektrikli araçlar", "basit elektrikli araçları tanımak", 3, Color(0xFF0288D1), Color(0xFF0288D1))
)

// 3. Sınıf Matematik
val grade3MathChapters = listOf(
    ChapterInfo("doğal sayılar", "doğal sayılar ve özellikleri", 3, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("doğal sayılarda işlemler", "doğal sayılarla toplama, çıkarma, çarpma ve bölme", 3, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("doğal sayılarda işlemler 2", "doğal sayılarla daha karmaşık işlemler", 3, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("kesirler", "kesirleri tanıma ve işlemler", 3, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("geometri", "geometrik şekiller ve özellikleri", 3, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("uzunluk, çevre ve alan", "geometrik ölçümler ve hesaplamalar", 3, Color(0xFF1976D2), Color(0xFF1976D2))
)

// 3. Sınıf Türkçe
val grade3TurkishChapters = listOf(
    ChapterInfo("çocuk dünyası", "çocuk dünyasını keşfetmek", 3, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("milli mücadele ve atatürk", "tarihimizde milli mücadele ve atatürk", 3, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("erdemler", "erdemli davranışlar ve değerler", 3, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("duygular", "duyguları tanımak ve ifade etmek", 3, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("kişisel gelişim", "kişisel gelişim ve becerileri geliştirmek", 3, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("milli kültürümüz", "milli kültür ve geleneklerimizi tanımak", 3, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("sanat", "sanat ve estetik değerleri keşfetmek", 3, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("doğa ve evren", "doğa olayları ve evreni anlamak", 3, Color(0xFFE91E63), Color(0xFFE91E63))
)


// 2. Sınıf Hayat Bilgisi
val grade2LifeChapters = listOf(
    ChapterInfo("ben ve okulum", "okul hayatını ve kendimizi tanımak", 2, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("sağlığım ve güvenliğim", "sağlıklı ve güvenli davranışlar", 2, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("ailem ve toplum", "aile ve toplum ilişkilerini öğrenmek", 2, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("yaşadığım yer ve ülkem", "çevremizi ve ülkemizi tanımak", 2, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("doğa ve çevre", "doğayı ve çevreyi korumak", 2, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("bilim, teknoloji ve sanat", "bilim, teknoloji ve sanatın hayatımızdaki yeri", 2, Color(0xFF009688), Color(0xFF009688))
)

// 2. Sınıf Matematik
val grade2MathChapters = listOf(
    ChapterInfo("nesnelerin geometrisi", "nesnelerin geometrik özelliklerini keşfetmek", 2, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("sayılar ve nicelikler", "sayıları ve miktarları tanımak", 2, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("işlemlerden cebirsel düşünmeye", "temel işlemler ve mantıksal düşünme", 2, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("işlemlerden cebirsel düşünmeye 2", "daha karmaşık işlemler ve düşünme", 2, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("sayılar ve nicelikler 2", "sayılar ve miktarlarla ileri işlemler", 2, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("nesnelerin geometrisi 2", "geometrik şekiller ve özellikleri", 2, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("veriye dayalı araştırma", "veri toplama ve yorumlama", 2, Color(0xFF1976D2), Color(0xFF1976D2))
)

// 2. Sınıf Türkçe
val grade2TurkishChapters = listOf(
    ChapterInfo("değerlerimizle varız", "değerlerimizi öğrenmek ve uygulamak", 2, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("atatürk ve çocuk", "atatürk ve çocuk hakları", 2, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("doğada neler oluyor", "doğadaki olayları keşfetmek", 2, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("okuma serüvenimiz", "okuma becerilerini geliştirmek", 2, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("yeteneklerimizi tanıyoruz", "kendi yeteneklerimizi keşfetmek", 2, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("mücut çocuk", "yaratıcı ve meraklı çocuk aktiviteleri", 2, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("kültür hazinemiz", "kültürel değerlerimizi tanımak", 2, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("haklarımızı biliyoruz", "çocuk haklarını öğrenmek ve anlamak", 2, Color(0xFFE91E63), Color(0xFFE91E63))
)


// 1. Sınıf Hayat Bilgisi
val grade1LifeChapters = listOf(
    ChapterInfo("ben ve okulum", "okul hayatını ve kendimizi tanımak", 1, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("sağlığım ve güvenliğim", "sağlıklı ve güvenli davranışlar", 1, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("ailem ve toplum", "aile ve toplum ilişkilerini öğrenmek", 1, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("yaşadığım yer ve ülkem", "çevremizi ve ülkemizi tanımak", 1, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("doğa ve çevre", "doğayı ve çevreyi korumak", 1, Color(0xFF009688), Color(0xFF009688)),
    ChapterInfo("bilim, teknoloji ve sanat", "bilim, teknoloji ve sanatın hayatımızdaki yeri", 1, Color(0xFF009688), Color(0xFF009688))
)

// 1. Sınıf Matematik
val grade1MathChapters = listOf(
    ChapterInfo("nesnelerin geometrisi", "nesnelerin geometrik özelliklerini keşfetmek", 1, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("sayılar ve nicelikler", "sayıları ve miktarları tanımak", 1, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("sayılar ve nicelikler 2", "daha ileri sayı ve nicelik çalışmaları", 1, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("işlemlerden cebirsel düşünmeye", "temel işlemler ve mantıksal düşünme", 1, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("sayılar ve nicelikler 3", "sayı ve nicelik konularının pekiştirilmesi", 1, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("nesnelerin geometrisi 2", "geometrik şekiller ve özellikleri", 1, Color(0xFF1976D2), Color(0xFF1976D2)),
    ChapterInfo("veriye dayalı araştırma", "veri toplama ve yorumlama", 1, Color(0xFF1976D2), Color(0xFF1976D2))
)

// 1. Sınıf Türkçe
val grade1TurkishChapters = listOf(
    ChapterInfo("yeteneklerimizi keşfediyoruz", "kendi yeteneklerimizi keşfetmek", 1, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("minif kaşifler", "keşfetme ve merak duygusunu geliştirmek", 1, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("atalarımızın izleri", "tarihimizde atalarımızın izlerini öğrenmek", 1, Color(0xFFE91E63), Color(0xFFE91E63)),
    ChapterInfo("sorumluluklarımızın farkındayız", "sorumluluk bilinci ve davranışlar", 1, Color(0xFFE91E63), Color(0xFFE91E63))
)


// Chapter mapping by grade and subject
val chaptersByGradeAndSubject = mapOf(
    // Grade 1
    GradeSubjectKey(1, "Hayat Bilgisi") to grade1LifeChapters,
    GradeSubjectKey(1, "Matematik") to grade1MathChapters,
    GradeSubjectKey(1, "Türkçe") to grade1TurkishChapters,

    // Grade 2
    GradeSubjectKey(2, "Hayat Bilgisi") to grade2LifeChapters,
    GradeSubjectKey(2, "Matematik") to grade2MathChapters,
    GradeSubjectKey(2, "Türkçe") to grade2TurkishChapters,

    // Grade 3
    GradeSubjectKey(3, "Hayat Bilgisi") to grade3LifeChapters,
    GradeSubjectKey(3, "Matematik") to grade3MathChapters,
    GradeSubjectKey(3, "Türkçe") to grade3TurkishChapters,
    GradeSubjectKey(3, "Fen Bilimleri") to grade3ScienceChapters,

    // Grade 4
    GradeSubjectKey(4, "Matematik") to grade4MathChapters,
    GradeSubjectKey(4, "Türkçe") to grade4TurkishChapters,
    GradeSubjectKey(4, "Din Kültürü ve Ahlak Bilgisi") to grade4ReligionChapters,

    // Grade 5
    GradeSubjectKey(5, "Matematik") to grade5MathChapters,
    GradeSubjectKey(5, "Türkçe") to grade5TurkishChapters,
    GradeSubjectKey(5, "Fen Bilimleri") to grade5ScienceChapters,
    GradeSubjectKey(5, "Sosyal Bilgiler") to grade5SocialChapters,
    GradeSubjectKey(5, "Din Kültürü ve Ahlak Bilgisi") to grade5ReligionChapters,

    // Grade 6
    GradeSubjectKey(6, "Matematik") to grade6MathChapters,
    GradeSubjectKey(6, "Türkçe") to grade6TurkishChapters,
    GradeSubjectKey(6, "Fen Bilimleri") to grade6ScienceChapters,
    GradeSubjectKey(6, "Sosyal Bilgiler") to grade6SocialChapters,

    // Grade 7
    GradeSubjectKey(7, "Matematik") to grade7MathChapters,
    GradeSubjectKey(7, "Türkçe") to grade7TurkishChapters,
    GradeSubjectKey(7, "Fen Bilimleri") to grade7ScienceChapters,
    GradeSubjectKey(7, "Sosyal Bilgiler") to grade7SocialChapters,
    GradeSubjectKey(7, "Din Kültürü ve Ahlak Bilgisi") to grade7ReligionChapters,

    // Grade 8
    GradeSubjectKey(8, "Matematik") to grade8MathChapters,
    GradeSubjectKey(8, "Türkçe") to grade8TurkishChapters,
    GradeSubjectKey(8, "Fen Bilimleri") to grade8ScienceChapters,
    GradeSubjectKey(8, "İnkılap Tarihi ve Atatürkçülük") to grade8HistoryChapters,
    GradeSubjectKey(8, "Din Kültürü ve Ahlak Bilgisi") to grade8ReligionChapters,

    // Grade 9
    GradeSubjectKey(9, "Matematik") to grade9MathChapters,
    GradeSubjectKey(9, "Türk Dili ve Edebiyatı") to grade9TurkishChapters,
    GradeSubjectKey(9, "Fizik") to grade9PhysicsChapters,
    GradeSubjectKey(9, "Kimya") to grade9ChemistryChapters,
    GradeSubjectKey(9, "Biyoloji") to grade9BiologyChapters,
    GradeSubjectKey(9, "Coğrafya") to grade9GeographyChapters,
    GradeSubjectKey(9, "Din Kültürü ve Ahlak Bilgisi") to grade9ReligionChapters,
    GradeSubjectKey(9, "Tarih") to grade9HistoryChapters,

    // Grade 10
    GradeSubjectKey(10, "Matematik") to grade10MathChapters,
    GradeSubjectKey(10, "Türk Dili ve Edebiyatı") to grade10TurkishChapters,
    GradeSubjectKey(10, "Fizik") to grade10PhysicsChapters,
    GradeSubjectKey(10, "Kimya") to grade10ChemistryChapters,
    GradeSubjectKey(10, "Biyoloji") to grade10BiologyChapters,
    GradeSubjectKey(10, "Coğrafya") to grade10GeographyChapters,
    GradeSubjectKey(10, "Felsefe") to grade10PhilosophyChapters,
    GradeSubjectKey(10, "Tarih") to grade10HistoryChapters,

    // Grade 11
    GradeSubjectKey(11, "Matematik") to grade11MathChapters,
    GradeSubjectKey(11, "Türk Dili ve Edebiyatı") to grade11TurkishChapters,
    GradeSubjectKey(11, "Fizik") to grade11PhysicsChapters,
    GradeSubjectKey(11, "Kimya") to grade11ChemistryChapters,
    GradeSubjectKey(11, "Biyoloji") to grade11BiologyChapters,
    GradeSubjectKey(11, "Coğrafya") to grade11GeographyChapters,
    GradeSubjectKey(11, "Din Kültürü ve Ahlak Bilgisi") to grade11ReligionChapters,
    GradeSubjectKey(11, "Tarih") to grade11HistoryChapters,

    // Grade 12
    GradeSubjectKey(12, "Matematik") to grade12MathChapters,
    GradeSubjectKey(12, "Türk Dili ve Edebiyatı") to grade12TurkishChapters,
    GradeSubjectKey(12, "Fizik") to grade12PhysicsChapters,
    GradeSubjectKey(12, "Kimya") to grade12ChemistryChapters,
    GradeSubjectKey(12, "Biyoloji") to grade12BiologyChapters,
    GradeSubjectKey(12, "Coğrafya") to grade12GeographyChapters,
    GradeSubjectKey(12, "Din Kültürü ve Ahlak Bilgisi") to grade12ReligionChapters,
    GradeSubjectKey(12, "Tarih") to grade12HistoryChapters,
)

// Default chapters for backward compatibility
val chapters = grade9MathChapters







































