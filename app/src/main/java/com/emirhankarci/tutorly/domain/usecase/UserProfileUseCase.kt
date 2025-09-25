package com.emirhankarci.tutorly.domain.usecase

import com.emirhankarci.tutorly.data.model.UserProfile
import com.emirhankarci.tutorly.data.repository.FirestoreRepository
import javax.inject.Inject

class UserProfileUseCase @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) {
    suspend fun createUser(user: UserProfile): Result<String> {
        return firestoreRepository.createUser(user)
    }

    suspend fun getUserById(userId: String): Result<UserProfile?> {
        return firestoreRepository.getUserById(userId)
    }

    suspend fun updateUser(userId: String, user: UserProfile): Result<Unit> {
        return firestoreRepository.updateUser(userId, user)
    }
}
