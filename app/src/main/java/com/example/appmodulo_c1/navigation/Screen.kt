package com.example.appmodulo_c1.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object BookingDetails : Screen("booking_details/{hotelId}") {
        fun createRoute(hotelId: Int) = "booking_details/$hotelId"
    }
    object BookingConfirm : Screen("booking_confirm/{hotelId}/{roomId}") {
        fun createRoute(hotelId: Int, roomId: Int) = "booking_confirm/$hotelId/$roomId"
    }
    object MyBookings : Screen("my_bookings")
}