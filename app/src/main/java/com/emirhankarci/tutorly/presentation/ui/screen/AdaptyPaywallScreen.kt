package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhankarci.tutorly.R

@Composable
fun AdaptyPaywallScreen() {
    var selectedPlan by remember { mutableStateOf("monthly") }

    val orangeColor = Color(0xFFFF9D42)
    val darkBlue = Color(0xFF1A2B4A)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.3f))

            // Header Section
            Text(
                text = "Unlock Your\nPotential",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = darkBlue,
                textAlign = TextAlign.Center,
                lineHeight = 38.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Choose a plan to start learning today.",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Weekly Plan Card
            PlanCard(
                title = "Weekly",
                price = "$9.99",
                period = "/week",
                isSelected = selectedPlan == "weekly",
                orangeColor = orangeColor,
                darkBlue = darkBlue,
                showBadge = false,
                onClick = { selectedPlan = "weekly" }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Monthly Plan Card
            PlanCard(
                title = "Monthly",
                price = "$29.99",
                period = "/month",
                isSelected = selectedPlan == "monthly",
                orangeColor = orangeColor,
                darkBlue = darkBlue,
                showBadge = true,
                onClick = { selectedPlan = "monthly" }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Start Learning Button
            Button(
                onClick = { /* Handle start learning */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = orangeColor
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Start Learning",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Restore Purchase Button
            Button(
                onClick = { /* Handle restore purchase */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD1D5DB)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Restore Purchase",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.weight(0.2f))
        }
    }
}

@Composable
fun PlanCard(
    title: String,
    price: String,
    period: String,
    isSelected: Boolean,
    orangeColor: Color,
    darkBlue: Color,
    showBadge: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Main Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (isSelected) orangeColor.copy(alpha = 0.15f)
                    else Color.White
                )
                .border(
                    width = if (isSelected) 3.dp else 1.dp,
                    color = if (isSelected) orangeColor else Color(0xFFE5E7EB),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable(onClick = onClick)
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = darkBlue
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = price,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = darkBlue
                    )
                    Text(
                        text = period,
                        fontSize = 14.sp,
                        color = darkBlue,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }

        // Best Value Badge
        if (showBadge) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-16).dp, y = (-8).dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(orangeColor)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Best Value",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

// Preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAdaptyPaywallScreen() {
    MaterialTheme {
        AdaptyPaywallScreen()
    }
}