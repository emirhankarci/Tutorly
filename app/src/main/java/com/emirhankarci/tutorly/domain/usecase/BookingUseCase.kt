package com.emirhankarci.tutorly.domain.usecase

import com.emirhankarci.tutorly.data.model.Booking
import com.emirhankarci.tutorly.data.repository.FirestoreRepository
import javax.inject.Inject

class BookingUseCase @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) {
    suspend fun createBooking(booking: Booking): Result<String> {
        return firestoreRepository.createBooking(booking)
    }

    suspend fun getBookingById(bookingId: String): Result<Booking?> {
        return firestoreRepository.getBookingById(bookingId)
    }

    suspend fun getBookingsByStudent(studentId: String): Result<List<Booking>> {
        return firestoreRepository.getBookingsByStudent(studentId)
    }

    suspend fun getBookingsByTutor(tutorId: String): Result<List<Booking>> {
        return firestoreRepository.getBookingsByTutor(tutorId)
    }

    suspend fun updateBooking(bookingId: String, booking: Booking): Result<Unit> {
        return firestoreRepository.updateBooking(bookingId, booking)
    }

    suspend fun deleteBooking(bookingId: String): Result<Unit> {
        return firestoreRepository.deleteBooking(bookingId)
    }
}
