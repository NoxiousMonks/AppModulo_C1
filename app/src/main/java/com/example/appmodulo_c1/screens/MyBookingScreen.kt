package com.example.appmodulo_c1.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmodulo_c1.viewmodel.Booking
import com.example.appmodulo_c1.BookingRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBookingsScreen(
    onBack: () -> Unit
) {
    val bookings by BookingRepository.bookings.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My bookings", fontSize = 16.sp, color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF0F0F0) // Светло-серый фон
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Subtitle
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE0E0E0)) // Чуть более темный серый под заголовок
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "List of my bookings",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            if (bookings.isEmpty()) {
                Text(
                    text = "No bookings found.",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(bookings) { index, booking ->
                        BookingItemCard(sequence = index + 1, booking = booking)
                    }
                }
            }
        }
    }
}

@Composable
fun BookingItemCard(sequence: Int, booking: Booking) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Large Number on the left
            Text(
                text = sequence.toString(),
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterVertically)
            )

            // Content Column
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${booking.firstName} ${booking.lastName}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = booking.hotelName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = "${booking.checkInDate} to ${booking.checkOutDate}",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "${booking.adults} Adults, ${booking.children} Children, ${booking.rooms} Room",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = booking.travelPurpose.label.replace(
                            " + € 150",
                            ""
                        ), // Убираем цену из лейбла для компактности
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Pay with ${booking.paymentMethod.label.lowercase()}",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }
            }

            // Huge Price on the right bottom
            Box(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = "€ ${booking.totalPrice}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}