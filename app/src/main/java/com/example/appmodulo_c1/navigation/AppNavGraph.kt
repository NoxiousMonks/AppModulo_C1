package com.example.appmodulo_c1.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.appmodulo_c1.BookingDetailsScreen
import com.example.appmodulo_c1.HomeScreen
import com.example.appmodulo_c1.navigation.Screen
import com.example.appmodulo_c1.viewmodel.BookingDetailsViewModel
import com.example.appmodulo_c1.viewmodel.MainViewModel


@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    viewModelBook: BookingDetailsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onHotelClick = { hotelId ->
                    navController.navigate(Screen.BookingDetails.createRoute(hotelId))
                },
                onMyBookingsClick = {
                    navController.navigate(Screen.MyBookings.route)

                },
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.BookingDetails.route,
            arguments = listOf(navArgument("hotelId") { type = NavType.IntType })
        ) { backStackEntry ->
            val hotelId = backStackEntry.arguments?.getInt("hotelId") ?: return@composable
            BookingDetailsScreen(
                hotelId = hotelId,
                onBack = { navController.popBackStack() },
                onRoomSelected = { roomId ->
                    navController.navigate(Screen.BookingConfirm.createRoute(hotelId, roomId))
                },
                viewModel = viewModelBook
            )
        }

        composable(
            route = Screen.BookingConfirm.route,
            arguments = listOf(
                navArgument("hotelId") { type = NavType.IntType },
                navArgument("roomId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val hotelId = backStackEntry.arguments?.getInt("hotelId") ?: return@composable
            val roomId = backStackEntry.arguments?.getInt("roomId") ?: return@composable
            BookingConfirmScreen(
                hotelId = hotelId,
                roomId = roomId,
                onBack = { navController.popBackStack() },
                onBookingConfirmed = {
                    navController.navigate(Screen.MyBookings.route) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }

        composable(Screen.MyBookings.route) {
            MyBookingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}