package com.roamly.app.feature.preferences

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roamly.app.feature.preferneces.TravelPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PreferencesUiState {
    data object Idle : PreferencesUiState()
    data object Loading : PreferencesUiState()
    data object Success : PreferencesUiState()
    data class Error(val message: String) : PreferencesUiState()
}

@HiltViewModel
class TravelPreferencesViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    // A Set, not a List — toggling membership (add/remove on tap) is the natural
    // operation here, and Sets make "is this already selected?" a cheap lookup
    var selectedStyles by mutableStateOf(setOf<String>())
        private set
    var selectedDestinations by mutableStateOf(setOf<String>())
        private set
    var budgetRange by mutableStateOf(500f..5000f)
        private set
    var travelPace by mutableStateOf("Balanced")
        private set

    private val _uiState = MutableStateFlow<PreferencesUiState>(PreferencesUiState.Idle)
    val uiState: StateFlow<PreferencesUiState> = _uiState.asStateFlow()

    fun toggleStyle(style: String) {
        selectedStyles = if (style in selectedStyles) selectedStyles - style else selectedStyles + style
    }

    fun toggleDestination(destination: String) {
        selectedDestinations = if (destination in selectedDestinations) selectedDestinations - destination else selectedDestinations + destination
    }

    fun updateBudgetRange(range: ClosedFloatingPointRange<Float>) {
        budgetRange = range
    }

    fun updateTravelPace(pace: String) {
        travelPace = pace
    }

    fun savePreferences() {
        viewModelScope.launch {
            _uiState.value = PreferencesUiState.Loading
            try {
                preferencesRepository.savePreferences(
                    TravelPreferences(
                        travelStyles = selectedStyles.toList(),
                        favoriteDestinations = selectedDestinations.toList(),
                        budgetMin = budgetRange.start.toInt(),
                        budgetMax = budgetRange.endInclusive.toInt(),
                        travelPace = travelPace
                    )
                )
                _uiState.value = PreferencesUiState.Success
            } catch (e: Exception) {
                _uiState.value = PreferencesUiState.Error(e.message ?: "Failed to save preferences")
            }
        }
    }
}