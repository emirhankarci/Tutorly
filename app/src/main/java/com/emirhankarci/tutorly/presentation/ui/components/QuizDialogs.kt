package com.emirhankarci.tutorly.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun QuestionCountDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var selectedCount by remember { mutableIntStateOf(5) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎯",
                    fontSize = 48.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Quiz Oluştur",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Kaç soru ile quiz oluşturmak istiyorsun?",
                    fontSize = 16.sp,
                    color = Color(0xFF6B7280),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Question count options
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val options = listOf(3, 5, 7, 10)
                    options.forEach { count ->
                        QuestionCountOption(
                            count = count,
                            isSelected = selectedCount == count,
                            onClick = { selectedCount = count }
                        )
                        if (count != options.last()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("İptal")
                    }

                    Button(
                        onClick = { onConfirm(selectedCount) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Devam Et")
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionFormatDialog(
    questionCount: Int,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var selectedFormat by remember { mutableStateOf("Test") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "📝",
                    fontSize = 48.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Soru Formatı Seçin",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "$questionCount soruluk quiz için hangi format?",
                    fontSize = 16.sp,
                    color = Color(0xFF6B7280),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Format options
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    QuestionFormatOption(
                        title = "Test Soruları",
                        description = "4 şıklı çoktan seçmeli sorular",
                        icon = "📋",
                        isSelected = selectedFormat == "Test",
                        onClick = { selectedFormat = "Test" }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    QuestionFormatOption(
                        title = "Açık Uçlu Sorular",
                        description = "Yazılı cevap gerektiren sorular",
                        icon = "✏️",
                        isSelected = selectedFormat == "Open",
                        onClick = { selectedFormat = "Open" }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Geri")
                    }

                    Button(
                        onClick = { onConfirm(selectedFormat) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Quiz Başlat")
                    }
                }
            }
        }
    }
}

@Composable
private fun QuestionCountOption(
    count: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                onClick = onClick,
                role = Role.RadioButton
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF6366F1).copy(alpha = 0.1f) else Color(0xFFF9FAFB)
        ),
        border = if (isSelected) CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF6366F1)),
            width = 2.dp
        ) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "$count Soru",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isSelected) Color(0xFF6366F1) else Color(0xFF374151)
                )

                val duration = when (count) {
                    3 -> "~5 dakika"
                    5 -> "~8 dakika"
                    7 -> "~12 dakika"
                    10 -> "~18 dakika"
                    else -> "~${count * 2} dakika"
                }

                Text(
                    text = "Tahmini süre: $duration",
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280)
                )
            }

            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFF6366F1)
                )
            )
        }
    }
}

@Composable
private fun QuestionFormatOption(
    title: String,
    description: String,
    icon: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                onClick = onClick,
                role = Role.RadioButton
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF6366F1).copy(alpha = 0.1f) else Color(0xFFF9FAFB)
        ),
        border = if (isSelected) CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF6366F1)),
            width = 2.dp
        ) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                fontSize = 24.sp,
                modifier = Modifier.padding(end = 12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isSelected) Color(0xFF6366F1) else Color(0xFF374151)
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFF6366F1)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionCountDialogPreview() {
    MaterialTheme {
        QuestionCountDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionFormatDialogPreview() {
    MaterialTheme {
        QuestionFormatDialog(
            questionCount = 5,
            onDismiss = {},
            onConfirm = {}
        )
    }
}