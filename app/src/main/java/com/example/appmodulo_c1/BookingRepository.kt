package com.example.appmodulo_c1


//import com.hotel.app.data.model.Booking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object BookingRepository {

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    fun addBooking(booking: Booking) {
        val current = _bookings.value.toMutableList()
        val newBooking = booking.copy(id = current.size + 1)
        current.add(newBooking)
        _bookings.value = current
    }

    fun getAllBookings(): List<Booking> = _bookings.value
}