package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emirhankarci.tutorly.data.model.Course
import com.emirhankarci.tutorly.presentation.viewmodel.CourseViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirestoreTestScreen(
    viewModel: CourseViewModel = hiltViewModel()
) {
    val courses by viewModel.courses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    
    var titleText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }
    var subjectText by remember { mutableStateOf("") }
    var gradeText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Firestore Test Ekrani",
            style = MaterialTheme.typography.headlineMedium
        )

        // Ders ekleme formu
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Yeni Ders Ekle",
                    style = MaterialTheme.typography.titleMedium
                )
                
                OutlinedTextField(
                    value = titleText,
                    onValueChange = { titleText = it },
                    label = { Text("Ders Başlığı") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = descriptionText,
                    onValueChange = { descriptionText = it },
                    label = { Text("Açıklama") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = subjectText,
                    onValueChange = { subjectText = it },
                    label = { Text("Ders") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = gradeText,
                    onValueChange = { gradeText = it },
                    label = { Text("Sınıf") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Button(
                    onClick = {
                        if (titleText.isNotBlank() && subjectText.isNotBlank()) {
                            val course = Course(
                                title = titleText,
                                description = descriptionText,
                                subject = subjectText,
                                grade = gradeText,
                                tutorId = "test_tutor_id",
                                tutorName = "Test Öğretmen",
                                price = 100.0,
                                duration = 60,
                                createdAt = Date()
                            )
                            viewModel.createCourse(course)
                            
                            // Formu temizle
                            titleText = ""
                            descriptionText = ""
                            subjectText = ""
                            gradeText = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading && titleText.isNotBlank() && subjectText.isNotBlank()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp))
                    } else {
                        Text("Ders Ekle")
                    }
                }
            }
        }

        // Hata mesajı
        errorMessage?.let { error ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = "Hata: $error",
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Dersler listesi
        Card(
            modifier = Modifier.fillMaxWidth()
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
                        text = "Dersler (${courses.size})",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Row {
                        Button(
                            onClick = { viewModel.getCoursesBySubject("Matematik") },
                            enabled = !isLoading
                        ) {
                            Text("Matematik")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { viewModel.getCoursesByGrade("9") },
                            enabled = !isLoading
                        ) {
                            Text("9. Sınıf")
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(courses) { course ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text(
                                        text = course.title,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        text = "${course.subject} - ${course.grade}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    if (course.description.isNotBlank()) {
                                        Text(
                                            text = course.description,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                    Text(
                                        text = "Öğretmen: ${course.tutorName}",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
