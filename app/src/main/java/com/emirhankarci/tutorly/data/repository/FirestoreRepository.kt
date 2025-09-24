package com.emirhankarci.tutorly.data.repository

import com.emirhankarci.tutorly.data.model.Booking
import com.emirhankarci.tutorly.data.model.Course
import com.emirhankarci.tutorly.data.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    // User Operations
    suspend fun createUser(user: UserProfile): Result<String> {
        return try {
            val docRef = firestore.collection("users").add(user).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserById(userId: String): Result<UserProfile?> {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            val user = document.toObject(UserProfile::class.java)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUser(userId: String, user: UserProfile): Result<Unit> {
        return try {
            firestore.collection("users").document(userId).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Course Operations
    suspend fun createCourse(course: Course): Result<String> {
        return try {
            val docRef = firestore.collection("courses").add(course).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCourseById(courseId: String): Result<Course?> {
        return try {
            val document = firestore.collection("courses").document(courseId).get().await()
            val course = document.toObject(Course::class.java)
            Result.success(course)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCoursesBySubject(subject: String): Result<List<Course>> {
        return try {
            val querySnapshot = firestore.collection("courses")
                .whereEqualTo("subject", subject)
                .whereEqualTo("isActive", true)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val courses = querySnapshot.toObjects(Course::class.java)
            Result.success(courses)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCoursesByGrade(grade: String): Result<List<Course>> {
        return try {
            val querySnapshot = firestore.collection("courses")
                .whereEqualTo("grade", grade)
                .whereEqualTo("isActive", true)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val courses = querySnapshot.toObjects(Course::class.java)
            Result.success(courses)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCoursesByTutor(tutorId: String): Result<List<Course>> {
        return try {
            val querySnapshot = firestore.collection("courses")
                .whereEqualTo("tutorId", tutorId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val courses = querySnapshot.toObjects(Course::class.java)
            Result.success(courses)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateCourse(courseId: String, course: Course): Result<Unit> {
        return try {
            firestore.collection("courses").document(courseId).set(course).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteCourse(courseId: String): Result<Unit> {
        return try {
            firestore.collection("courses").document(courseId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Booking Operations
    suspend fun createBooking(booking: Booking): Result<String> {
        return try {
            val docRef = firestore.collection("bookings").add(booking).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBookingById(bookingId: String): Result<Booking?> {
        return try {
            val document = firestore.collection("bookings").document(bookingId).get().await()
            val booking = document.toObject(Booking::class.java)
            Result.success(booking)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBookingsByStudent(studentId: String): Result<List<Booking>> {
        return try {
            val querySnapshot = firestore.collection("bookings")
                .whereEqualTo("studentId", studentId)
                .orderBy("scheduledDateTime", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val bookings = querySnapshot.toObjects(Booking::class.java)
            Result.success(bookings)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBookingsByTutor(tutorId: String): Result<List<Booking>> {
        return try {
            val querySnapshot = firestore.collection("bookings")
                .whereEqualTo("tutorId", tutorId)
                .orderBy("scheduledDateTime", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val bookings = querySnapshot.toObjects(Booking::class.java)
            Result.success(bookings)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateBooking(bookingId: String, booking: Booking): Result<Unit> {
        return try {
            firestore.collection("bookings").document(bookingId).set(booking).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteBooking(bookingId: String): Result<Unit> {
        return try {
            firestore.collection("bookings").document(bookingId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
