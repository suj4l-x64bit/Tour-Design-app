package com.roamly.app.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Represents every possible state the Sign In / Sign Up screen can be in.
// A sealed class instead of separate Booleans (isLoading, hasError, isSuccess) —
// because with Booleans, invalid combinations are possible (isLoading=true AND isSuccess=true).
// With a sealed class, the UI can only ever be in exactly one of these states.
sealed class AuthUiState {
    data object Idle : AuthUiState()
    data object Loading : AuthUiState()
    data object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun signIn(email: String, password: String) {
        // viewModelScope ties this coroutine's lifetime to the ViewModel —
        // it auto-cancels if the screen is destroyed mid-request, so you never
        // update state on a screen that's no longer there
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                authRepository.signIn(email, password)
                _uiState.value = AuthUiState.Success
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Sign in failed")
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                authRepository.signUp(email, password)
                _uiState.value = AuthUiState.Success
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Sign up failed")
            }
        }
    }
}