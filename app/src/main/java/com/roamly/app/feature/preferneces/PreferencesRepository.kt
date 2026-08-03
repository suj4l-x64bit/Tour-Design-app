package com.roamly.app.feature.preferences

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.roamly.app.feature.preferneces.TravelPreferences
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun savePreferences(preferences: TravelPreferences) {
        val uid = firebaseAuth.currentUser?.uid
            ?: throw IllegalStateException("No signed-in user")

        // Path: users/{uid}/preferences/travel_preferences
        // Firestore auto-creates collections/documents on first write — no separate "create table" step
        firestore.collection("users")
            .document(uid)
            .collection("preferences")
            .document("travel_preferences")
            .set(preferences)
            .await()
    }
}
