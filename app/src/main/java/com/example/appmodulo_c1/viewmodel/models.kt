package com.example.appmodulo_c1.viewmodel

data class Hotel(
    val id: Int,
    val name: String,
    val rating: Float,
    val distanceKm: Double,
    val imageRes: Int? = null,
    val ratings: HotelRatings,
    val reviews: List<Review>,
    val rooms: List<Room>
)

data class HotelRatings(
    val location: Float,
    val staff: Float,
    val valueForMoney: Float
)

data class Review(
    val reviewerName: String,
    val nationality: String,
    val content: String
)

data class Room(
    val id: Int,
    val roomType: String,
    val bedType: String,
    val totalGuests: Int,
    val features: String,
    val pricePerNight: Int
)

data class Booking(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val hotelName: String,
    val checkInDate: String,
    val checkOutDate: String,
    val roomType: String,
    val adults: Int,
    val children: Int,
    val rooms: Int,
    val travelPurpose: TravelPurpose,
    val paymentMethod: PaymentMethod,
    val totalPrice: Int
)

enum class TravelPurpose(val label: String, val extraCost: Int) {
    SIGHTSEEING("For sightseeing", 0),
    BUSINESS("For business with a meeting room", 150)
}

enum class PaymentMethod(val label: String) {
    CASH("Cash"),
    CREDIT_CARD("Credit card"),
    E_PAY("E-Pay")
}