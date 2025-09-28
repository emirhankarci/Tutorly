package com.emirhankarci.tutorly.domain.repository

import com.emirhankarci.tutorly.domain.entity.UserProfile

interface UserProfileRepository {
    suspend fun saveUserProfile(userId: String, profile: UserProfile): Result<Unit>
    suspend fun getUserProfile(userId: String): Result<UserProfile?>
    suspend fun isProfileCompleted(userId: String): Result<Boolean>
    suspend fun deleteUserProfile(userId: String): Result<Unit>
}