package com.example.appmodulo_c1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appmodulo_c1.BookingRepository
import com.example.appmodulo_c1.HotelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

data class BookingFormState(
    val firstName: String = "",
    val lastName: String = "",
    val checkInDate: String = "Tue, Sep 10, 2024",
    val checkOutDate: String = "Sun, Sep 15, 2024",
    val adults: String = "1",
    val children: String = "0",
    val travelPurpose: TravelPurpose = TravelPurpose.SIGHTSEEING,
    val paymentMethod: PaymentMethod = PaymentMethod.CASH,
    // Errors
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val checkInError: String? = null,
    val checkOutError: String? = null,
    val adultsError: String? = null,
    val childrenError: String? = null
)

class BookingConfirmViewModel : ViewModel() {

    private val _hotel = MutableStateFlow<Hotel?>(null)
    val hotel: StateFlow<Hotel?> = _hotel.asStateFlow()

    private val _room = MutableStateFlow<Room?>(null)
    val room: StateFlow<Room?> = _room.asStateFlow()

    private val _formState = MutableStateFlow(BookingFormState())
    val formState: StateFlow<BookingFormState> = _formState.asStateFlow()

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice: StateFlow<Int> = _totalPrice.asStateFlow()

    private val _roomCount = MutableStateFlow(1)
    val roomCount: StateFlow<Int> = _roomCount.asStateFlow()

    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog: StateFlow<Boolean> = _showSuccessDialog.asStateFlow()

    private val _errorMessages = MutableStateFlow<List<String>>(emptyList())
    val errorMessages: StateFlow<List<String>> = _errorMessages.asStateFlow()

    fun load(hotelId: Int, roomId: Int) {
        _hotel.value = HotelRepository.getHotelById(hotelId)
        _room.value = _hotel.value?.rooms?.find { it.id == roomId }
        recalculatePrice()
    }

    fun onFirstNameChange(value: String) {
        _formState.value = _formState.value.copy(firstName = value, firstNameError = null)
    }

    fun onLastNameChange(value: String) {
        _formState.value = _formState.value.copy(lastName = value, lastNameError = null)
    }

    fun onCheckInChange(value: String) {
        _formState.value = _formState.value.copy(checkInDate = value, checkInError = null)
        recalculatePrice()
    }

    fun onCheckOutChange(value: String) {
        _formState.value = _formState.value.copy(checkOutDate = value, checkOutError = null)
        recalculatePrice()
    }

    fun onAdultsChange(value: String) {
        _formState.value = _formState.value.copy(adults = value, adultsError = null)
        recalculateRooms()
        recalculatePrice()
    }

    fun onChildrenChange(value: String) {
        _formState.value = _formState.value.copy(children = value, childrenError = null)
        recalculateRooms()
        recalculatePrice()
    }

    fun onTravelPurposeChange(purpose: TravelPurpose) {
        _formState.value = _formState.value.copy(travelPurpose = purpose)
        recalculatePrice()
    }

    fun onPaymentMethodChange(method: PaymentMethod) {
        _formState.value = _formState.value.copy(paymentMethod = method)
    }

    private fun recalculateRooms() {
        val adults = _formState.value.adults.toIntOrNull() ?: 1
        val children = _formState.value.children.toIntOrNull() ?: 0
        val guestsPerRoom = _room.value?.totalGuests ?: 2
        _roomCount.value = ((adults + children + guestsPerRoom - 1) / guestsPerRoom).coerceAtLeast(1)
    }

    private fun recalculatePrice() {
        val basePrice = _room.value?.pricePerNight ?: 0
        val rooms = _roomCount.value
        val nights = calculateNights()
        val purposeExtra = _formState.value.travelPurpose.extraCost
        _totalPrice.value = (basePrice * rooms * nights) + (purposeExtra * rooms)
    }

    private fun calculateNights(): Int {
        return try {
            val formatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy", java.util.Locale.ENGLISH)
            val checkIn = LocalDate.parse(_formState.value.checkInDate, formatter)
            val checkOut = LocalDate.parse(_formState.value.checkOutDate, formatter)
            ChronoUnit.DAYS.between(checkIn, checkOut).coerceAtLeast(1).toInt()
        } catch (e: Exception) {
            5 // default 5 nights if dates can't be parsed
        }
    }

    fun normalizeDate(raw: String): String {
        val outputFormatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy", java.util.Locale.ENGLISH)
        val formats = listOf(
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy"),
            DateTimeFormatter.ofPattern("MMM dd yyyy", java.util.Locale.ENGLISH),
            DateTimeFormatter.ofPattern("EEE, MMM d, yyyy", java.util.Locale.ENGLISH)
        )
        for (fmt in formats) {
            try {
                val date = LocalDate.parse(raw.trim(), fmt)
                return date.format(outputFormatter)
            } catch (_: DateTimeParseException) {}
        }
        return raw
    }

    fun onBookNow() {
        val errors = mutableListOf<String>()
        val form = _formState.value
        var updated = form

        // First name
        if (form.firstName.isBlank()) {
            updated = updated.copy(firstNameError = "First Name is required")
            errors.add("First Name is required")
        } else if (!form.firstName.all { it.isLetter() || it.isWhitespace() }) {
            updated = updated.copy(firstNameError = "Only letters allowed")
            errors.add("First Name: only letters allowed")
        } else if (form.firstName.length !in 1..15) {
            updated = updated.copy(firstNameError = "Length must be 1-15")
            errors.add("First Name length must be 1-15")
        }

        // Last name
        if (form.lastName.isBlank()) {
            updated = updated.copy(lastNameError = "Last Name is required")
            errors.add("Last Name is required")
        } else if (!form.lastName.all { it.isLetter() || it.isWhitespace() }) {
            updated = updated.copy(lastNameError = "Only letters allowed")
            errors.add("Last Name: only letters allowed")
        } else if (form.lastName.length !in 1..15) {
            updated = updated.copy(lastNameError = "Length must be 1-15")
            errors.add("Last Name length must be 1-15")
        }

        // Adults
        val adultsVal = form.adults.toIntOrNull()
        if (adultsVal == null || adultsVal !in 1..5) {
            updated = updated.copy(adultsError = "Adults: 1-5")
            errors.add("Adults must be between 1 and 5")
        }

        // Children
        val childrenVal = form.children.toIntOrNull()
        if (childrenVal == null || childrenVal < 0) {
            updated = updated.copy(childrenError = "Children: 0 or more")
            errors.add("Children must be 0 or more")
        } else if (adultsVal != null && childrenVal > adultsVal * 2) {
            updated = updated.copy(childrenError = "Max: 2x adults")
            errors.add("Children cannot exceed 2x number of adults")
        }

        // Dates
        if (form.checkInDate.isBlank()) {
            updated = updated.copy(checkInError = "Check-in date required")
            errors.add("Check-in date is required")
        }
        if (form.checkOutDate.isBlank()) {
            updated = updated.copy(checkOutError = "Check-out date required")
            errors.add("Check-out date is required")
        }
        if (form.checkInDate.isNotBlank() && form.checkOutDate.isNotBlank()) {
            try {
                val fmt = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy", java.util.Locale.ENGLISH)
                val cin = LocalDate.parse(form.checkInDate, fmt)
                val cout = LocalDate.parse(form.checkOutDate, fmt)
                if (!cin.isBefore(cout)) {
                    updated = updated.copy(checkInError = "Check-in must be before check-out")
                    errors.add("Check-in date must be earlier than check-out date")
                }
            } catch (e: Exception) {
                updated = updated.copy(checkInError = "Invalid date format")
                errors.add("Invalid date format. Use: Tue, Sep 10, 2024")
            }
        }

        _formState.value = updated

        if (errors.isEmpty()) {
            saveBooking()
            _showSuccessDialog.value = true
        } else {
            _errorMessages.value = errors
        }
    }

    fun dismissSuccessDialog() {
        _showSuccessDialog.value = false
    }

    fun dismissErrors() {
        _errorMessages.value = emptyList()
    }

    private fun saveBooking() {
        val form = _formState.value
        val hotel = _hotel.value ?: return
        val room = _room.value ?: return
        BookingRepository.addBooking(
            Booking(
                id = 0,
                firstName = form.firstName,
                lastName = form.lastName,
                hotelName = hotel.name,
                checkInDate = form.checkInDate,
                checkOutDate = form.checkOutDate,
                roomType = room.roomType,
                adults = form.adults.toIntOrNull() ?: 1,
                children = form.children.toIntOrNull() ?: 0,
                rooms = _roomCount.value,
                travelPurpose = form.travelPurpose,
                paymentMethod = form.paymentMethod,
                totalPrice = _totalPrice.value
            )
        )
    }
}