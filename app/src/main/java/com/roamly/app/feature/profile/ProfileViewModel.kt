package com.roamly.app.feature.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    // Firebase doesn't always have a displayName set (email/password signup doesn't
    // ask for one) — fall back to the part of the email before @ so it's never blank
    val displayName: String
        get() = firebaseAuth.currentUser?.displayName?.takeIf { it.isNotBlank() }
            ?: firebaseAuth.currentUser?.email?.substringBefore("@")
            ?: "Traveler"

    val email: String
        get() = firebaseAuth.currentUser?.email ?: ""

    fun signOut() {
        firebaseAuth.signOut()
    }
}