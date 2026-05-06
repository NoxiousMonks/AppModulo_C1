package com.example.appmodulo_c1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.appmodulo_c1.navigation.AppNavGraph
import com.example.appmodulo_c1.ui.theme.AppModulo_C1Theme
import com.example.appmodulo_c1.viewmodel.BookingDetailsViewModel
import com.example.appmodulo_c1.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val bookingDetailsViewModel: BookingDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppModulo_C1Theme {
                val navController = rememberNavController() // Правильная инициализация навигации
                AppNavGraph(
                    navController = navController,
                    viewModel = mainViewModel,
                    viewModelBook = bookingDetailsViewModel
                )
            }
        }
    }
}