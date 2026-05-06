package com.example.appmodulo_c1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appmodulo_c1.HotelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _hotels = MutableStateFlow<List<Hotel>>(HotelRepository.hotels)
    val hotels: StateFlow<List<Hotel>> = _hotels.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _hotels.value = HotelRepository.searchHotels(query)
    }

    fun onSearchSubmit() {
        _hotels.value = HotelRepository.searchHotels(_searchQuery.value)
    }

    ///////////////////

}