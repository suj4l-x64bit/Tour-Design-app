package com.roamly.app.feature.home

data class Destination(
    val name: String,
    val location: String,
    val rating: Float,
    val priceFrom: Int,
    val days: Int,
    val imageUrl: String
)

val sampleDestinations = listOf(
    Destination("Santorini", "Greece", 4.8f, 1200, 5, "https://picsum.photos/seed/santorini/800/500"),
    Destination("Bali", "Indonesia", 4.7f, 900, 6, "https://picsum.photos/seed/bali/800/500"),
    Destination("Kyoto", "Japan", 4.9f, 1400, 4, "https://picsum.photos/seed/kyoto/800/500"),
    Destination("Reykjavik", "Iceland", 4.6f, 1600, 5, "https://picsum.photos/seed/iceland/800/500")
)

val categoryOptions = listOf("Beach", "City", "Mountain", "Near Trips")