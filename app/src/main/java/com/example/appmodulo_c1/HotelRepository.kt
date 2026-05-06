package com.example.appmodulo_c1


object HotelRepository {

    val hotels: List<Hotel> = listOf(
        Hotel(
            id = 1000,
            name = "Grand Alps Resort",
            rating = 9.2f,
            distanceKm = 0.75,
            ratings = HotelRatings(location = 8.6f, staff = 9.7f, valueForMoney = 8.9f),
            reviews = listOf(
                Review(
                    reviewerName = "Kevin",
                    nationality = "United States of America",
                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean fringilla facilisis sem nec ornare. Fusce sed facilisis est, vel lobortis nisi. Nullam porta dui in accumsan molestie. Aliquam iaculis justo augue."
                ),
                Review(
                    reviewerName = "Wevin",
                    nationality = "Canada",
                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean fringilla facilisis sem nec ornare. Fusce sed facilisis est, vel lobortis nisi. Nullam porta dui in accumsan molestie. Aliquam iaculis justo augue."
                ),
                Review(
                    reviewerName = "Anna",
                    nationality = "Germany",
                    content = "Absolutely stunning location with breathtaking views of the Alps. The staff were incredibly helpful and the rooms were spotless. Would highly recommend to anyone visiting the area."
                )
            ),
            rooms = listOf(
                Room(
                    id = 1,
                    roomType = "Double Room with Matterhorn View",
                    bedType = "1 king bed",
                    totalGuests = 2,
                    features = "32 m², Big TV, Free WiFi, Mountain View, Refrigerator, Free soft drinks",
                    pricePerNight = 299
                ),
                Room(
                    id = 2,
                    roomType = "Double Room with Matterhorn View",
                    bedType = "1 king bed",
                    totalGuests = 2,
                    features = "32 m², Big TV, Free WiFi, Mountain View, Refrigerator, Free soft drinks",
                    pricePerNight = 299
                ),
                Room(
                    id = 3,
                    roomType = "Suite with Alpine Panorama",
                    bedType = "2 queen beds",
                    totalGuests = 4,
                    features = "55 m², Big TV, Free WiFi, Panoramic View, Jacuzzi, Minibar, Free breakfast",
                    pricePerNight = 499
                ),
                Room(
                    id = 4,
                    roomType = "Standard Single Room",
                    bedType = "1 single bed",
                    totalGuests = 1,
                    features = "18 m², TV, Free WiFi, City View, Refrigerator",
                    pricePerNight = 149
                )
            )
        ),
        Hotel(
            id = 1001,
            name = "Alpine Bliss Hotel",
            rating = 8.7f,
            distanceKm = 0.75,
            ratings = HotelRatings(location = 8.0f, staff = 9.0f, valueForMoney = 8.5f),
            reviews = listOf(
                Review(
                    reviewerName = "Maria",
                    nationality = "France",
                    content = "A wonderful stay with beautiful mountain views. The breakfast was exceptional."
                )
            ),
            rooms = listOf(
                Room(
                    id = 1,
                    roomType = "Standard Double Room",
                    bedType = "1 double bed",
                    totalGuests = 2,
                    features = "25 m², TV, Free WiFi, Garden View",
                    pricePerNight = 189
                )
            )
        ),
        Hotel(
            id = 1002,
            name = "Mont Blanc Lodge",
            rating = 9.0f,
            distanceKm = 0.75,
            ratings = HotelRatings(location = 9.2f, staff = 8.8f, valueForMoney = 9.0f),
            reviews = listOf(
                Review(
                    reviewerName = "Hans",
                    nationality = "Switzerland",
                    content = "Perfect location right next to the ski lift. Cozy atmosphere and friendly staff."
                )
            ),
            rooms = listOf(
                Room(
                    id = 1,
                    roomType = "Cozy Mountain Cabin Room",
                    bedType = "1 king bed",
                    totalGuests = 2,
                    features = "28 m², Fireplace, Free WiFi, Mountain View",
                    pricePerNight = 259
                )
            )
        ),
        Hotel(
            id = 1003,
            name = "Chalet Élégance",
            rating = 8.5f,
            distanceKm = 0.75,
            ratings = HotelRatings(location = 8.3f, staff = 8.7f, valueForMoney = 8.4f),
            reviews = listOf(
                Review(
                    reviewerName = "Sophie",
                    nationality = "Belgium",
                    content = "Charming chalet with authentic alpine decor. Great value for money."
                )
            ),
            rooms = listOf(
                Room(
                    id = 1,
                    roomType = "Chalet Double Room",
                    bedType = "1 king bed",
                    totalGuests = 2,
                    features = "30 m², Wood décor, Free WiFi, Balcony",
                    pricePerNight = 229
                )
            )
        ),
        Hotel(
            id = 1004,
            name = "Summit Peak Hotel",
            rating = 9.1f,
            distanceKm = 0.75,
            ratings = HotelRatings(location = 9.5f, staff = 9.2f, valueForMoney = 8.7f),
            reviews = listOf(
                Review(
                    reviewerName = "Lucas",
                    nationality = "Netherlands",
                    content = "Top notch service and amazing views. The spa was a wonderful bonus!"
                )
            ),
            rooms = listOf(
                Room(
                    id = 1,
                    roomType = "Premium Summit Room",
                    bedType = "1 king bed",
                    totalGuests = 2,
                    features = "40 m², Spa access, Free WiFi, Panoramic View",
                    pricePerNight = 399
                )
            )
        ),
        Hotel(
            id = 1008,
            name = "Glacier View Inn",
            rating = 8.9f,
            distanceKm = 0.75,
            ratings = HotelRatings(location = 9.0f, staff = 8.5f, valueForMoney = 9.1f),
            reviews = listOf(
                Review(
                    reviewerName = "Emma",
                    nationality = "United Kingdom",
                    content = "Stunning glacier views from our room. The restaurant serves amazing local cuisine."
                ),
                Review(
                    reviewerName = "Pierre",
                    nationality = "France",
                    content = "Excellent hotel with all modern amenities. The location is perfect for skiing."
                )
            ),
            rooms = listOf(
                Room(
                    id = 1,
                    roomType = "Glacier View Double",
                    bedType = "1 king bed",
                    totalGuests = 2,
                    features = "35 m², Big TV, Free WiFi, Glacier View, Minibar",
                    pricePerNight = 349
                ),
                Room(
                    id = 2,
                    roomType = "Family Room",
                    bedType = "2 double beds",
                    totalGuests = 4,
                    features = "50 m², Big TV, Free WiFi, Mountain View, Play area",
                    pricePerNight = 449
                ),
                Room(
                    id = 3,
                    roomType = "Economy Single",
                    bedType = "1 single bed",
                    totalGuests = 1,
                    features = "15 m², TV, Free WiFi",
                    pricePerNight = 119
                )
            )
        )
    )

    fun getHotelById(id: Int): Hotel? = hotels.find { it.id == id }

    fun searchHotels(query: String): List<Hotel> {
        return if (query.isBlank()) hotels
        else hotels.filter { it.name.contains(query, ignoreCase = true) }
    }
}