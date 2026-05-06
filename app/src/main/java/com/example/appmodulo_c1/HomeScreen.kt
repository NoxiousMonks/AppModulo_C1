package com.example.appmodulo_c1

import com.example.appmodulo_c1.ui.theme.Background
import com.example.appmodulo_c1.ui.theme.BookButtonColor
import com.example.appmodulo_c1.ui.theme.DividerColor
import com.example.appmodulo_c1.ui.theme.OnBackground
import com.example.appmodulo_c1.ui.theme.OnPrimary
import com.example.appmodulo_c1.ui.theme.OnSurface
import com.example.appmodulo_c1.ui.theme.OnSurfaceVariant
import com.example.appmodulo_c1.ui.theme.Primary
import com.example.appmodulo_c1.ui.theme.StarColor
import com.example.appmodulo_c1.ui.theme.SurfaceE
import com.example.appmodulo_c1.ui.theme.SurfaceVariant
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmodulo_c1.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onHotelClick: (Int) -> Unit,
    onMyBookingsClick: () -> Unit,
    viewModel: MainViewModel
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val hotels by viewModel.hotels.collectAsState()

    Scaffold(
        topBar = {
            HomeTopBar(onMyBookingsClick = onMyBookingsClick)
        },
        containerColor = Background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HotelSearchBar(
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                onSearch = viewModel::onSearchSubmit,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(hotels, key = { it.id }) { hotel ->
                    HotelListItem(
                        hotel = hotel,
                        onBookClick = { onHotelClick(hotel.id) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(onMyBookingsClick: () -> Unit) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "The Alps' Hotel",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = OnBackground
                )
                // French flag emoji representation
                Text(text = "🇫🇷", fontSize = 18.sp)
            }
        },
        actions = {
            IconButton(onClick = onMyBookingsClick) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "My Bookings",
                    tint = OnBackground
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SurfaceE
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HotelSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Search a hotel name",
                color = OnSurfaceVariant,
                fontSize = 14.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = OnSurfaceVariant
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Primary,
            unfocusedBorderColor = DividerColor,
            focusedContainerColor = SurfaceE,
            unfocusedContainerColor = SurfaceE
        )
    )
}

@Composable
private fun HotelListItem(
    hotel: Hotel,
    onBookClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceE)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HotelImage(
                modifier = Modifier.size(80.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = hotel.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = OnSurface
                )
                HotelStarRating(rating = hotel.rating)
                Text(
                    text = "${hotel.distanceKm} km from Alps' ski lift",
                    fontSize = 11.sp,
                    color = OnSurfaceVariant
                )
            }

            Button(
                onClick = onBookClick,
                colors = ButtonDefaults.buttonColors(containerColor = BookButtonColor),
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Book it",
                    fontSize = 12.sp,
                    color = OnPrimary
                )
            }
        }
    }
}

@Composable
private fun HotelImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(SurfaceVariant)
            .border(1.dp, DividerColor, RoundedCornerShape(6.dp)),
        contentAlignment = Alignment.Center
    ) {
        // Placeholder — replace with AsyncImage(coil) when real images are available
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            tint = OnSurfaceVariant,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
private fun HotelStarRating(
    rating: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = rating.toString(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = OnSurface
        )
        val fullStars = (rating / 2).toInt()
        repeat(5) { index ->
            Icon(
                imageVector = if (index < fullStars) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = StarColor,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}