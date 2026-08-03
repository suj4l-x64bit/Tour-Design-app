package com.roamly.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.roamly.app.feature.preferences.TravelPreferencesScreen
import com.roamly.app.feature.onboarding.OnboardingScreen
import com.roamly.app.feature.onboarding.SplashScreen
import com.roamly.app.feature.auth.SignInScreen
import com.roamly.app.feature.auth.SignUpScreen
import com.roamly.app.feature.home.HomeScreen
import com.roamly.app.feature.main.MainScreen

@Composable
fun RoamlyNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onTimeout = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onContinueWithEmail = { navController.navigate(Screen.SignIn.route) },
                onContinueWithGoogle = { /* TODO: wire once Google Sign-In is implemented */ },
                onContinueWithApple = { /* TODO: wire once Apple Sign-In is implemented */ }
            )
        }
        composable(Screen.SignIn.route) {
            SignInScreen(
                onSignInSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) },
                onContinueWithGoogle = { /* TODO: wire once Google Sign-In is implemented */ },
                onContinueWithApple = { /* TODO: wire once Apple Sign-In is implemented */ }
            )
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(Screen.TravelPreferences.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToSignIn = { navController.navigate(Screen.SignIn.route) },
                onContinueWithGoogle = { /* TODO: wire once Google Sign-In is implemented */ },
                onContinueWithApple = { /* TODO: wire once Apple Sign-In is implemented */ }
            )
        }
        composable(Screen.Home.route) {
            MainScreen(
                onLogout = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.TravelPreferences.route) {
            TravelPreferencesScreen(
                onDone = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
    }
}