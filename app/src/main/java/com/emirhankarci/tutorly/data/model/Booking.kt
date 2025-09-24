package com.emirhankarci.tutorly.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Booking(
    @DocumentId
    val id: String = "",
    val courseId: String = "",
    val studentId: String = "",
    val tutorId: String = "",
    val scheduledDateTime: Date? = null,
    val status: BookingStatus = BookingStatus.PENDING,
    val notes: String = "",
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null
)

enum class BookingStatus {
    PENDING,
    CONFIRMED,
    COMPLETED,
    CANCELLED
}
