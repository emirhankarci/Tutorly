package com.emirhankarci.tutorly.data.model

import com.google.firebase.firestore.DocumentId

data class GradeSummary(
    @DocumentId
    val id: String = "",
    val chapter: String = "",
    val color: String = "#2196F3",
    val duration: Int = 0,
    val grade: Int = 0,
    val subject: String = "",
    val summary: String = "",
    val title: String = ""
)


