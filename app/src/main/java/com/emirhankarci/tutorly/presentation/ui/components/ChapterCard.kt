package com.emirhankarci.tutorly.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhankarci.tutorly.domain.entity.ChapterInfo

@Composable
fun ChapterCard(
    chapterInfo: ChapterInfo,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = chapterInfo.borderColor.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Chapter icon box
            Box(
                modifier = Modifier
                    .size(55.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(chapterInfo.backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = chapterInfo.title,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Chapter content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                // Chapter title
                Text(
                    text = chapterInfo.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = chapterInfo.backgroundColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Chapter description
                Text(
                    text = chapterInfo.description,
                    fontSize = 12.sp,
                    color = Color(0xFF9197A2)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Lesson count
                Text(
                    text = "${chapterInfo.lessonCount} Ders",
                    fontSize = 12.sp,
                    color = Color(0xFF9197A2),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}