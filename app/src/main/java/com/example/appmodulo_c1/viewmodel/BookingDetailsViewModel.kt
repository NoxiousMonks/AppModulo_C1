package com.example.appmodulo_c1.viewmodel


import androidx.lifecycle.ViewModel
import com.example.appmodulo_c1.HotelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookingDetailsViewModel : ViewModel() {

    private val _hotel = MutableStateFlow<Hotel?>(null)
    val hotel: StateFlow<Hotel?> = _hotel.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    fun loadHotel(hotelId: Int) {
        _hotel.value = HotelRepository.getHotelById(hotelId)
    }

    fun selectTab(index: Int) {
        _selectedTab.value = index
    }
}