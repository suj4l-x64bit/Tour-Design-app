package com.roamly.app.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object SignIn : Screen("sign_in")
    data object SignUp : Screen("sign_up")
    data object TravelPreferences : Screen("travel_preferences")
    data object Home : Screen("home")
    data object AiTripPlanner : Screen("ai_trip_planner")
    data object TripDetails : Screen("trip_details/{tripId}") {
        fun createRoute(tripId: String) = "trip_details/$tripId"
    }
    data object Itinerary : Screen("itinerary/{tripId}") {
        fun createRoute(tripId: String) = "itinerary/$tripId"
    }
    data object ExplorePlaces : Screen("explore_places")
    data object BudgetTracker : Screen("budget_tracker/{tripId}") {
        fun createRoute(tripId: String) = "budget_tracker/$tripId"
    }
    data object TravelMemories : Screen("travel_memories")
    data object Profile : Screen("profile")
}