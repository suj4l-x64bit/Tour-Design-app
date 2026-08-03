package com.roamly.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CardTravel
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person

// Separate from Screen.kt on purpose — these routes live inside their own nested
// NavController (see MainScreen.kt), so they never collide with top-level routes
sealed class BottomNavScreen(val route: String, val label: String, val icon: ImageVector) {
    data object Explore : BottomNavScreen("explore_tab", "Explore", Icons.Filled.Explore)
    data object Trips : BottomNavScreen("trips_tab", "Trips", Icons.Filled.CardTravel)
    data object Planner : BottomNavScreen("planner_tab", "Planner", Icons.Filled.AutoAwesome)
    data object Saved : BottomNavScreen("saved_tab", "Saved", Icons.Filled.Bookmark)
    data object Profile : BottomNavScreen("profile_tab", "Profile", Icons.Filled.Person)

}