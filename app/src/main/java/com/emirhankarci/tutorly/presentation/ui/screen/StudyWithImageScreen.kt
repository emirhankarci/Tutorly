package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.emirhankarci.tutorly.presentation.viewmodel.StudyWithImageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyWithImageScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    viewModel: StudyWithImageViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var promptText by remember { mutableStateOf("") }

    // Image viewer state
    var showImageViewer by remember { mutableStateOf(false) }
    var selectedImageIndex by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        // Content
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Prompt Input Section
            item {
                Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor =Color(0xFFF0F9FF)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Öğrenmek istediğin dersi tarif et",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color =  Color(0xFF0723A4),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            OutlinedTextField(
                                value = promptText,

                                onValueChange = { promptText = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = {
                                    Text(
                                        text = "Örn:Fizikte vektörler konusunu baştan sona detaylıca anlat.",

                                        color = Color.Gray
                                    )
                                },
                                minLines = 3,
                                maxLines = 5,
                                shape = RoundedCornerShape(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedBorderColor = Color.White,
                                    unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                                    cursorColor = Color(0xFF5E35B1),
                                    focusedTextColor = Color(0xFF2D3748),
                                    unfocusedTextColor = Color(0xFF2D3748),
                                    focusedPlaceholderColor = Color.Gray,
                                    unfocusedPlaceholderColor = Color.Gray
                                )
                            )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                if (promptText.isNotBlank()) {
                                    viewModel.generateImage(promptText)
                                }
                            },
                            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp),
                            shape = RoundedCornerShape(10.dp),
                            enabled = promptText.isNotBlank() && !uiState.isLoading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor =Color(0xFF0723A4),
                                disabledContainerColor = Color.White,
                                disabledContentColor = Color.Black,
                                contentColor = Color.White,

                            )
                        ) {
                            if (uiState.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text(
                                text = "Konuyu Görsellerle Anlat",

                            )
                        }
                    }
                }
            }

            // Generated Images Section
            item {
                if (uiState.allImages.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Generated Images (${uiState.totalImages})",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF2D3748)
                                )

                                IconButton(
                                    onClick = {
                                        if (promptText.isNotBlank()) {
                                            viewModel.generateImage(promptText)
                                        }
                                    },
                                    enabled = !uiState.isLoading
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "Regenerate",
                                        tint = Color(0xFF667eea)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Grid of all images
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.height(((uiState.allImages.size / 2 + uiState.allImages.size % 2) * 130).dp)
                            ) {
                                items(uiState.allImages) { imageUrl ->
                                    val imageIndex = uiState.allImages.indexOf(imageUrl)
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(120.dp)
                                            .clickable {
                                                selectedImageIndex = imageIndex
                                                showImageViewer = true
                                            },
                                        shape = RoundedCornerShape(8.dp),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                                    ) {
                                        AsyncImage(
                                            model = imageUrl,
                                            contentDescription = "Generated educational image",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }

                         /*   if (uiState.prompt.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Prompt: ${uiState.prompt}",
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }*/
                        }
                    }
                }
            }

            // Error Message
            item {
                val errorMessage = uiState.errorMessage
                if (errorMessage != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Error",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFD32F2F)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = errorMessage,
                                fontSize = 14.sp,
                                color = Color(0xFFD32F2F)
                            )
                        }
                    }
                }
            }

            // Instructions Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F9FF)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "💡 Daha iyi sonuçlar için ipuçları",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1565C0)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "• Ne görmek istediğini ayrıntılı yaz\n" +
                                    "• Eğitim bağlamını belirt (şema, grafik vb.)\n" +
                                    "• Etiketlerin/metinlerin Türkçe olmasını iste\n" +
                                    "• Renk veya stil tercihlerini ekle",
                            fontSize = 14.sp,
                            color = Color(0xFF1565C0)
                        )
                    }
                }
            }
        }
    }

    // Full-screen Image Viewer Dialog
    if (showImageViewer && uiState.allImages.isNotEmpty()) {
        ImageViewerDialog(
            images = uiState.allImages,
            initialIndex = selectedImageIndex,
            onDismiss = { showImageViewer = false }
        )
    }
}

@Composable
fun ImageViewerDialog(
    images: List<String>,
    initialIndex: Int,
    onDismiss: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(initialIndex) }
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale = (scale * zoomChange).coerceIn(0.5f, 5f)
        offset += offsetChange
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.9f))
        ) {
            // Close button
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(48.dp)
                    .background(
                        Color.Black.copy(alpha = 0.5f),
                        RoundedCornerShape(24.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }

            // Image counter
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.7f)
                )
            ) {
                Text(
                    text = "${currentIndex + 1} / ${images.size}",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 14.sp
                )
            }

            // Main image
            AsyncImage(
                model = images[currentIndex],
                contentDescription = "Full screen image",
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
                    .transformable(state = transformState),
                contentScale = ContentScale.Fit
            )

            // Navigation buttons
            if (images.size > 1) {
                // Previous button
                if (currentIndex > 0) {
                    IconButton(
                        onClick = {
                            currentIndex--
                            scale = 1f
                            offset = Offset.Zero
                        },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(16.dp)
                            .size(48.dp)
                            .background(
                                Color.Black.copy(alpha = 0.5f),
                                RoundedCornerShape(24.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Previous",
                            tint = Color.White
                        )
                    }
                }

                // Next button
                if (currentIndex < images.size - 1) {
                    IconButton(
                        onClick = {
                            currentIndex++
                            scale = 1f
                            offset = Offset.Zero
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(16.dp)
                            .size(48.dp)
                            .background(
                                Color.Black.copy(alpha = 0.5f),
                                RoundedCornerShape(24.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next",
                            tint = Color.White
                        )
                    }
                }
            }

            // Reset zoom on double tap instruction
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.7f)
                )
            ) {
                Text(
                    text = "Pinch to zoom • Drag to pan • Tap arrows to navigate",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}