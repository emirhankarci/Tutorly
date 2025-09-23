package com.emirhankarci.tutorly.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhankarci.tutorly.domain.entity.GradeInfo

@Composable
fun GradeCard(
    gradeInfo: GradeInfo,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .aspectRatio(1f)
            .border(
                width = 2.dp,
                color = gradeInfo.borderColor.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Companion.White),
    ) {
        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Grade number box
            Box(
                modifier = Modifier.Companion
                    .size(55.dp)
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                    .background(gradeInfo.backgroundColor),
                contentAlignment = Alignment.Companion.Center
            ) {
                Text(
                    text = gradeInfo.number.toString(),
                    color = Color.Companion.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Companion.Bold
                )
            }

            Spacer(modifier = Modifier.Companion.height(8.dp))

            // Grade title
            Text(
                text = gradeInfo.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Companion.Bold,
                color = gradeInfo.backgroundColor,
                textAlign = TextAlign.Companion.Center
            )

            // Grade subtitle
            Text(
                text = gradeInfo.subtitle,
                fontSize = 12.sp,
                color = Color(0xFF9197A2),
                textAlign = TextAlign.Companion.Center
            )
        }
    }
}