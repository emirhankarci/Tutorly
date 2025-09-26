package com.emirhankarci.tutorly.data.repository

import com.emirhankarci.tutorly.domain.entity.AppUser
import com.emirhankarci.tutorly.domain.entity.UserProgress
import com.emirhankarci.tutorly.domain.entity.UserScheduleItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private val usersCollection = firestore.collection("users")

    suspend fun createUserProfile(user: AppUser): Result<Unit> {
        return try {
            usersCollection
                .document(user.uid)
                .set(user)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserProfile(uid: String): Result<AppUser> {
        return try {
            val document = usersCollection
                .document(uid)
                .get()
                .await()

            if (document.exists()) {
                val user = document.toObject(AppUser::class.java)
                if (user != null) {
                    Result.success(user)
                } else {
                    Result.failure(Exception("Failed to parse user data"))
                }
            } else {
                Result.failure(Exception("User profile not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserProgress(uid: String, progress: UserProgress): Result<Unit> {
        return try {
            usersCollection
                .document(uid)
                .update("study_progress", progress)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addScheduleItem(uid: String, scheduleItem: UserScheduleItem): Result<Unit> {
        return try {
            // Get current schedule items
            val currentUser = getUserProfile(uid).getOrThrow()
            val updatedScheduleItems = currentUser.scheduleItems + scheduleItem

            // Update the document
            usersCollection
                .document(uid)
                .update("schedule_items", updatedScheduleItems)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateScheduleItem(uid: String, scheduleItem: UserScheduleItem): Result<Unit> {
        return try {
            // Get current schedule items
            val currentUser = getUserProfile(uid).getOrThrow()
            val updatedScheduleItems = currentUser.scheduleItems.map {
                if (it.id == scheduleItem.id) scheduleItem else it
            }

            // Update the document
            usersCollection
                .document(uid)
                .update("schedule_items", updatedScheduleItems)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteScheduleItem(uid: String, itemId: String): Result<Unit> {
        return try {
            // Get current schedule items
            val currentUser = getUserProfile(uid).getOrThrow()
            val updatedScheduleItems = currentUser.scheduleItems.filter { it.id != itemId }

            // Update the document
            usersCollection
                .document(uid)
                .update("schedule_items", updatedScheduleItems)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun observeUserProfile(uid: String): Flow<AppUser?> = callbackFlow {
        val listener = usersCollection
            .document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val user = snapshot?.toObject(AppUser::class.java)
                trySend(user)
            }

        awaitClose {
            listener.remove()
        }
    }

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}