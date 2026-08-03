package com.roamly.app.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.roamly.app.feature.home.HomeScreen
import com.roamly.app.feature.profile.ProfileScreen
import com.roamly.app.navigation.BottomNavScreen

private val bottomNavItems = listOf(
    BottomNavScreen.Explore,
    BottomNavScreen.Trips,
    BottomNavScreen.Planner,
    BottomNavScreen.Saved,
    BottomNavScreen.Profile
)

@Composable
fun MainScreen(onLogout: () -> Unit) {
    val bottomNavController = rememberNavController()

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            FloatingBottomNavBar(
                items = bottomNavItems,
                isSelected = { item ->
                    currentDestination?.hierarchy?.any { it.route == item.route } == true
                },
                onItemClick = { item ->
                    bottomNavController.navigate(item.route) {
                        popUpTo(bottomNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavScreen.Explore.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavScreen.Explore.route) { HomeScreen() }
            composable(BottomNavScreen.Trips.route) { PlaceholderTab("Trips") }
            composable(BottomNavScreen.Planner.route) { PlaceholderTab("AI Trip Planner") }
            composable(BottomNavScreen.Saved.route) { PlaceholderTab("Saved / Travel Memories") }
            composable(BottomNavScreen.Profile.route) { ProfileScreen(onLogout = onLogout) }
        }
    }
}

@Composable
private fun PlaceholderTab(name: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("$name screen coming soon")
    }
}