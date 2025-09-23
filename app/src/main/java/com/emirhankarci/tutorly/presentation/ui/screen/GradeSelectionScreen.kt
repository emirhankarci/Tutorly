package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class GradeInfo(
    val number: Int,
    val title: String,
    val subtitle: String,
    val backgroundColor: Color,
    val borderColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeSelectionScreen() {
    val grades = listOf(
        GradeInfo(12, "Grade 12", "Senior Year", Color(0xFF1976D2), Color(0xFF1976D2)),
        GradeInfo(11, "Grade 11", "Junior Year", Color(0xFF4CAF50), Color(0xFF4CAF50)),
        GradeInfo(10, "Grade 10", "Sophomore", Color(0xFFE91E63), Color(0xFFE91E63)),
        GradeInfo(9, "Grade 9", "Freshman", Color(0xFFFF5722), Color(0xFFFF5722)),
        GradeInfo(8, "Grade 8", "Middle School", Color(0xFFFFC107), Color(0xFFFFC107)),
        GradeInfo(7, "Grade 7", "Middle School", Color(0xFF673AB7), Color(0xFF673AB7)),
        GradeInfo(6, "Grade 6", "Elementary", Color(0xFF009688), Color(0xFF009688)),
        GradeInfo(5, "Grade 5", "Elementary", Color(0xFFFF9800), Color(0xFFFF9800)),
        GradeInfo(4, "Grade 4", "Elementary", Color(0xFF795548), Color(0xFF795548)),
        GradeInfo(3, "Grade 3", "Elementary", Color(0xFF607D8B), Color(0xFF607D8B)),
        GradeInfo(2, "Grade 2", "Elementary", Color(0xFF9C27B0), Color(0xFF9C27B0)),
        GradeInfo(1, "Grade 1", "Elementary", Color(0xFFFF6B6B), Color(0xFFFF6B6B))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Choose Grade",
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                    }
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        IconButton(
                            onClick = { /* Handle back navigation */ },
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            Color.White,
                                            Color(0xFFF8F9FA)
                                        )
                                    ),
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.White.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(18.dp)
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFF6B46C1),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(44.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFF8A80),
                            Color(0xFF81C784),
                            Color(0xFF81D4FA)
                        )
                    )
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "School",
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Text(
                text = "Select Your Grade Level",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF3C0A8D),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Choose your current grade to access relevant study \n materials",
                fontSize = 12.sp,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Grade grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(grades) { grade ->
                    GradeCard(
                        gradeInfo = grade,
                        onClick = { /* Handle grade selection */ }
                    )
                }
            }
        }
    }
}

@Composable
fun GradeCard(
    gradeInfo: GradeInfo,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .border(
                width = 2.dp,
                color = gradeInfo.borderColor.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Grade number box
            Box(
                modifier = Modifier
                    .size(55.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(gradeInfo.backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gradeInfo.number.toString(),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Grade title
            Text(
                text = gradeInfo.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = gradeInfo.backgroundColor,
                textAlign = TextAlign.Center
            )

            // Grade subtitle
            Text(
                text = gradeInfo.subtitle,
                fontSize = 12.sp,
                color = Color(0xFF9197A2),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GradeSelectionScreenPreview() {
    MaterialTheme {
        GradeSelectionScreen()
    }
}