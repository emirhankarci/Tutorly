package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FAQItem(
    val question: String,
    val answer: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQScreen(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val faqList = listOf(
        FAQItem(
            question = "Tutorly nedir?",
            answer = "Tutorly, yapay zeka destekli kişiselleştirilmiş öğrenme deneyimi sunan bir eğitim platformudur. AI teknolojisi ile konuları öğrenmenize, sınav hazırlığı yapmanıza ve ders programınızı düzenlemenize yardımcı olur."
        ),
        FAQItem(
            question = "Hangi konularda yardım alabilirim?",
            answer = "Tutorly, 9-12. sınıf seviyelerinde matematik, fen bilimleri, edebiyat ve daha birçok konuda destek sağlar. İstediğiniz herhangi bir konuda AI asistanımızla çalışabilirsiniz."
        ),
        FAQItem(
            question = "AI Chat özelliği nasıl çalışır?",
            answer = "AI Chat, seçtiğiniz konu hakkında sorular sormanıza ve anında cevaplar almanıza olanak tanır. Yapay zeka destekli asistanımız, konuyu anlamanızı kolaylaştırmak için detaylı açıklamalar ve örnekler sunar."
        ),
        FAQItem(
            question = "Quiz özelliği nedir?",
            answer = "Quiz özelliği ile öğrendiğiniz konularda kendinizi test edebilirsiniz. Çoktan seçmeli veya açık uçlu sorular oluşturarak bilginizi ölçebilir ve eksiklerinizi görebilirsiniz."
        ),
        FAQItem(
            question = "Görsel ile çalışma nasıl yapılır?",
            answer = "Görsel ile çalış özelliği sayesinde fotoğraf yükleyerek AI'dan o görselle ilgili açıklamalar ve çözümler alabilirsiniz. Özellikle matematik problemleri ve diyagramlar için çok kullanışlıdır."
        ),
        FAQItem(
            question = "Ders programı nasıl oluşturulur?",
            answer = "Ders programı oluşturmak için 'Çalışma Planı' bölümüne gidin. Manuel olarak ders ekleyebilir veya AI asistanından size özel bir program oluşturmasını isteyebilirsiniz."
        ),
        FAQItem(
            question = "İngilizce öğrenme özelliği nedir?",
            answer = "İngilizce öğrenme bölümünde kelime çalışabilir, gramer pratikleri yapabilir ve AI ile İngilizce konuşma pratiği gerçekleştirebilirsiniz."
        ),
        FAQItem(
            question = "Premium üyelik gereksiz midir?",
            answer = "Premium üyelik ile sınırsız AI sohbet, özel ders planları, detaylı ilerleme takibi ve reklamsız deneyim gibi ek özelliklere erişim sağlarsınız."
        ),
        FAQItem(
            question = "Verilerim güvende mi?",
            answer = "Evet! Tüm verileriniz şifreli olarak saklanır ve gizlilik politikamıza uygun şekilde işlenir. Verilerinizi asla üçüncü taraflarla paylaşmayız."
        ),
        FAQItem(
            question = "Hesabımı nasıl silebilirim?",
            answer = "Ayarlar sayfasından 'Hesabı Sil' butonuna tıklayarak hesabınızı kalıcı olarak silebilirsiniz. Bu işlem geri alınamaz ve tüm verileriniz silinir."
        ),
        FAQItem(
            question = "Teknik destek nasıl alabilirim?",
            answer = "Herhangi bir sorun yaşarsanız tutorly284@gmail.com adresinden bizimle iletişime geçebilirsiniz. Ekibimiz size en kısa sürede yardımcı olacaktır."
        ),
        FAQItem(
            question = "Uygulama hangi cihazlarda çalışır?",
            answer = "Tutorly, Android 7.0 ve üzeri sürümlere sahip tüm akıllı telefonlar ve tabletlerde sorunsuz çalışmaktadır."
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Top Bar
        TopAppBar(
            title = {
                Text(
                    text = "Sıkça Sorulan Sorular",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Geri",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF2196F3)
            )
        )

        // FAQ List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(faqList) { faqItem ->
                FAQItemCard(faqItem)
            }
        }
    }
}

@Composable
private fun FAQItemCard(faqItem: FAQItem) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = faqItem.question,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2C3E50),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Kapat" else "Aç",
                    tint = Color(0xFF2196F3)
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Text(
                    text = faqItem.answer,
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280),
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}

