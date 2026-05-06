package com.example.appmodulo_c1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmodulo_c1.viewmodel.Hotel
import com.example.appmodulo_c1.viewmodel.HotelRatings
import com.example.appmodulo_c1.viewmodel.Review
import com.example.appmodulo_c1.viewmodel.Room
import com.example.appmodulo_c1.ui.theme.Background
import com.example.appmodulo_c1.ui.theme.DividerColor
import com.example.appmodulo_c1.ui.theme.OnBackground
import com.example.appmodulo_c1.ui.theme.OnSurface
import com.example.appmodulo_c1.ui.theme.OnSurfaceVariant
import com.example.appmodulo_c1.ui.theme.Primary
import com.example.appmodulo_c1.ui.theme.RatingBarBackground
import com.example.appmodulo_c1.ui.theme.RatingBarColor
import com.example.appmodulo_c1.ui.theme.SurfaceE
import com.example.appmodulo_c1.ui.theme.SurfaceVariant
import com.example.appmodulo_c1.ui.theme.TabSelectedColor
import com.example.appmodulo_c1.ui.theme.TabUnselectedColor
import com.example.appmodulo_c1.viewmodel.BookingDetailsViewModel

//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.hotel.app.data.model.Hotel
//import com.hotel.app.data.model.Review
//import com.hotel.app.data.model.Room
//import com.hotel.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDetailsScreen(
    hotelId: Int,
    onBack: () -> Unit,
    onRoomSelected: (Int) -> Unit,
    viewModel: BookingDetailsViewModel
) {
    LaunchedEffect(hotelId) { viewModel.loadHotel(hotelId) }

    val hotel by viewModel.hotel.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

    Scaffold(
        topBar = {
            BookingDetailsTopBar(onBack = onBack)
        },
        containerColor = Background
    ) { padding ->
        hotel?.let { h ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                BookingTabRow(
                    selectedTab = selectedTab,
                    onTabSelected = viewModel::selectTab
                )
                HotelNameHeader(hotelName = h.name)
                when (selectedTab) {
                    0 -> GuestReviewsTab(hotel = h)
                    1 -> RoomSelectionTab(hotel = h, onRoomSelected = onRoomSelected)
                }


            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Primary)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookingDetailsTopBar(onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Booking",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceE)
    )
}

@Composable
private fun HotelNameHeader(hotelName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceE)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = hotelName,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 70.sp,
            color = OnBackground
        )
    }
}

@Composable
private fun BookingTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("Guest reviews", "Room selection")
    TabRow(
        selectedTabIndex = selectedTab,
        containerColor = SurfaceE,
        contentColor = TabSelectedColor,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                color = TabSelectedColor
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (selectedTab == index) TabSelectedColor else TabUnselectedColor
                    )
                }
            )
        }
    }
}

// ─── Guest Reviews Tab ────────────────────────────────────────────────────────

@Composable
private fun GuestReviewsTab(hotel: Hotel) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { RatingsSection(ratings = hotel.ratings) }
        item { ReviewsSection(reviews = hotel.reviews) }
    }
}

@Composable
private fun RatingsSection(ratings: HotelRatings) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceE)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Ratings",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = OnBackground
            )
            HorizontalDivider(color = DividerColor)
            RatingItem(label = "Location", score = ratings.location)
            RatingItem(label = "Staff", score = ratings.staff)
            RatingItem(label = "Value for money", score = ratings.valueForMoney)
        }
    }
}

@Composable
private fun RatingItem(label: String, score: Float) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 13.sp, color = OnSurface)
            Text(
                text = score.toString(),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Primary
            )
        }
        LinearProgressIndicator(
            progress = { score / 10f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = RatingBarColor,
            trackColor = RatingBarBackground
        )
    }
}

@Composable
private fun ReviewsSection(reviews: List<Review>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceE)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Reviews",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = OnBackground
            )
            HorizontalDivider(color = DividerColor)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(reviews) { review ->
                    ReviewCard(review = review)
                }
            }
        }
    }
}

@Composable
private fun ReviewCard(review: Review) {
    Card(
        modifier = Modifier.width(200.dp),
        shape = RoundedCornerShape(8.dp),
        border = CardDefaults.outlinedCardBorder(),
        colors = CardDefaults.cardColors(containerColor = SurfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ReviewerAvatar(name = review.reviewerName, Primary)
                Column {
                    Text(
                        text = review.reviewerName,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = OnSurface
                    )
                    Text(
                        text = review.nationality,
                        fontSize = 10.sp,
                        color = OnSurfaceVariant
                    )
                }
            }
            Text(
                text = review.content,
                fontSize = 11.sp,
                color = OnSurface,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun ReviewerAvatar(name: String, OnPrimary: Color) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name.firstOrNull()?.uppercase() ?: "?",
            color = OnPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

// ─── Room Selection Tab ───────────────────────────────────────────────────────

@Composable
private fun RoomSelectionTab(hotel: Hotel, onRoomSelected: (Int) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Rooms",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = OnBackground
            )
        }
        items(hotel.rooms, key = { it.id }) { room ->
            RoomListItem(room = room, onClick = { onRoomSelected(room.id) })
        }
    }
}

@Composable
private fun RoomListItem(room: Room, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceE)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = room.roomType,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = OnBackground
                )
                Text(
                    text = "Bed: ${room.bedType}  Total number of guests: ${room.totalGuests}",
                    fontSize = 11.sp,
                    color = OnSurface
                )
                Text(
                    text = room.features,
                    fontSize = 11.sp,
                    color = OnSurfaceVariant,
                    lineHeight = 16.sp
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "€ ${room.pricePerNight}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Primary
            )
        }
    }
}