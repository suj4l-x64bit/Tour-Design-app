package com.roamly.app.feature.preferneces

// Plain data class — Firestore's .set() converts this to a document automatically
// via reflection on the property names, no manual mapping needed
data class TravelPreferences(
    val travelStyles: List<String> = emptyList(),
    val favoriteDestinations: List<String> = emptyList(),
    val budgetMin: Int = 500,
    val budgetMax: Int = 5000,
    val travelPace: String = "Balanced"
)