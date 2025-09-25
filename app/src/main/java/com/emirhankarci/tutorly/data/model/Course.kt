package com.emirhankarci.tutorly.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Course(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val subject: String = "",
    val grade: String = "",
    val tutorId: String = "",
    val tutorName: String = "",
    val price: Double = 0.0,
    val duration: Int = 60, // dakika cinsinden
    val isActive: Boolean = true,
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null
)
