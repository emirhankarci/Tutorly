package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhankarci.tutorly.R
import com.emirhankarci.tutorly.domain.entity.chapters
import com.emirhankarci.tutorly.presentation.ui.components.ChapterCard

@Composable
fun ChapterSelectionScreen(
    modifier: Modifier = Modifier,
    onChapterSelected: (String) -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header icon and text
            Card(
                modifier = Modifier
                    .size(84.dp)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.subject_book),
                        contentDescription = "Chapter Icon",
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Text(
                text = "Konu Seçim Ekranı",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF3C0A8D),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Öğrenmek istediğin konuyu seç ve derslere başla.",
                fontSize = 12.sp,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Chapter list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(chapters) { chapter ->
                    ChapterCard(
                        chapterInfo = chapter,
                        onClick = { onChapterSelected(chapter.title) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChapterSelectionScreenPreview() {
    MaterialTheme {
        ChapterSelectionScreen()
    }
}