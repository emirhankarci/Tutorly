package com.emirhankarci.tutorly.domain.entity

data class AppUser(
    val uid: String = "",
    val displayName: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    val firstName: String = "",
    val lastName: String = ""
) {
    companion object {
        fun empty() = AppUser()
    }

    val fullName: String
        get() = when {
            firstName.isNotEmpty() && lastName.isNotEmpty() -> "$firstName $lastName"
            displayName.isNotEmpty() -> displayName
            else -> "User"
        }

    val greetingName: String
        get() = when {
            firstName.isNotEmpty() -> firstName
            displayName.isNotEmpty() -> displayName.split(" ").firstOrNull() ?: displayName
            else -> "there"
        }
}