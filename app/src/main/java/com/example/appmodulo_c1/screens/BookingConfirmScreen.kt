package com.example.appmodulo_c1.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmodulo_c1.viewmodel.PaymentMethod
import com.example.appmodulo_c1.viewmodel.TravelPurpose
import com.example.appmodulo_c1.viewmodel.BookingConfirmViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingConfirmScreen(
    hotelId: Int,
    roomId: Int,
    onBack: () -> Unit,
    onBookingConfirmed: () -> Unit,
    viewModel: BookingConfirmViewModel = viewModel()
) {
    LaunchedEffect(hotelId, roomId) {
        viewModel.load(hotelId, roomId)
    }

    val hotel by viewModel.hotel.collectAsState()
    val room by viewModel.room.collectAsState()
    val formState by viewModel.formState.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()
    val roomCount by viewModel.roomCount.collectAsState()
    val showSuccessDialog by viewModel.showSuccessDialog.collectAsState()
    val errorMessages by viewModel.errorMessages.collectAsState()

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissSuccessDialog() },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.dismissSuccessDialog()
                    onBookingConfirmed()
                }) { Text("OK") }
            },
            title = { Text("Success") },
            text = { Text("Your booking has been successfully confirmed!") }
        )
    }

    if (errorMessages.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissErrors() },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissErrors() }) { Text("OK") }
            },
            title = { Text("Please check your inputs") },
            text = { Text(errorMessages.joinToString("\n")) }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Booking Confirm", fontSize = 16.sp, color = Color.Black) },
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
        containerColor = Color(0xFFF0F0F0) // Светло-серый фон как на макете
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // Header
            item {
                Text(
                    text = "You are going to reserve:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            // Hotel Name
            item {
                Text(
                    text = hotel?.name ?: "Hotel name",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    color = Color.Black
                )
            }

            // Room Info Box
            item {
                OutlinedCard(
                    shape = RectangleShape,
                    border = BorderStroke(1.dp, Color.LightGray),
                    colors = CardDefaults.outlinedCardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = room?.roomType ?: "Room Type",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row {
                                Text("Bed: ", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("${room?.bedType ?: ""}   ", fontSize = 12.sp)
                                Text(
                                    "Total number of guests: ",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text("${room?.totalGuests ?: ""}", fontSize = 12.sp)
                            }
                            Text(
                                text = "€ ${room?.pricePerNight ?: 0}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            // Form Title
            item {
                Text(
                    text = "Form",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Names Row
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = formState.firstName,
                        onValueChange = viewModel::onFirstNameChange,
                        label = { Text("First Name") },
                        isError = formState.firstNameError != null,
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                    OutlinedTextField(
                        value = formState.lastName,
                        onValueChange = viewModel::onLastNameChange,
                        label = { Text("Last Name") },
                        isError = formState.lastNameError != null,
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                }
            }

            // Dates Row
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = formState.checkInDate,
                        onValueChange = viewModel::onCheckInChange,
                        label = { Text("Check-in date") },
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                    OutlinedTextField(
                        value = formState.checkOutDate,
                        onValueChange = viewModel::onCheckOutChange,
                        label = { Text("Check-out date") },
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                }
            }

            // Room Type Subtitle
            item {
                Text(
                    text = room?.roomType ?: "Room Type",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Guests and Rooms Row
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = formState.adults,
                        onValueChange = viewModel::onAdultsChange,
                        label = { Text("Adults") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                    OutlinedTextField(
                        value = formState.children,
                        onValueChange = viewModel::onChildrenChange,
                        label = { Text("Children") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                    OutlinedTextField(
                        value = roomCount.toString(),
                        onValueChange = {},
                        label = { Text("Room") },
                        readOnly = true,
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                }
            }

            // Travel for business Box
            item {
                OutlinedCard(
                    shape = RectangleShape,
                    border = BorderStroke(1.dp, Color.LightGray),
                    colors = CardDefaults.outlinedCardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Travel for business?", fontSize = 12.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = formState.travelPurpose == TravelPurpose.SIGHTSEEING,
                                onClick = { viewModel.onTravelPurposeChange(TravelPurpose.SIGHTSEEING) }
                            )
                            Text("For sightseeing", fontSize = 12.sp)

                            Spacer(modifier = Modifier.width(16.dp))

                            RadioButton(
                                selected = formState.travelPurpose == TravelPurpose.BUSINESS,
                                onClick = { viewModel.onTravelPurposeChange(TravelPurpose.BUSINESS) }
                            )
                            Column {
                                Text("+ € 150", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Text("For business with a meeting room", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            // Payment and Total Price Box
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedCard(
                        modifier = Modifier.weight(1.5f),
                        shape = RectangleShape,
                        border = BorderStroke(1.dp, Color.LightGray),
                        colors = CardDefaults.outlinedCardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Which way to pay?", fontSize = 12.sp)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                PaymentMethod.values().forEach { method ->
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        RadioButton(
                                            selected = formState.paymentMethod == method,
                                            onClick = { viewModel.onPaymentMethodChange(method) },
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Text(
                                            method.label,
                                            fontSize = 11.sp,
                                            modifier = Modifier.padding(start = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Huge Total Price
                    Text(
                        text = "€ $totalPrice",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.End)
                    )
                }
            }

            // Book Now Button
            item {
                Button(
                    onClick = { viewModel.onBookNow() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text(
                        text = "Book now",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}