package com.emirhankarci.tutorly.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class UserProfile(
    @DocumentId
    val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String = "",
    val userType: UserType = UserType.STUDENT,
    val subjects: List<String> = emptyList(), // öğretmenler için
    val grades: List<String> = emptyList(), // öğretmenler için
    val bio: String = "",
    val isActive: Boolean = true,
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null
)

enum class UserType {
    STUDENT,
    TUTOR
}
