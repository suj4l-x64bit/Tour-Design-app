package com.roamly.app.feature.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roamly.app.ui.theme.RoamlyBlue
import com.roamly.app.ui.theme.RoamlyBackground
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.roamly.app.R
import com.roamly.app.ui.theme.oswald

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onContinueWithGoogle: () -> Unit,
    onContinueWithApple: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            onSignUpSuccess()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.login_background),
            contentDescription = "Image background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(28.dp, 32.dp, 28.dp, 30.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Roamly",
                fontSize = 64.sp,
                fontFamily = oswald,
                fontWeight = FontWeight.Normal,
                color = RoamlyBlue,
            )
            Spacer(modifier = Modifier.height(0.dp))

            Text(text = "Create your account",
                 fontSize = 20.sp,
                 fontWeight = FontWeight.Bold,
                 color = Color.Black)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Start your journey with Roamly✈️",
                fontSize = 17.sp,
                color = Color(0xFF858894)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = {
                    Text(
                        "Full name",
                        color = Color(0xFF8B8E9A),
                        fontSize = 17.sp
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = RoamlyBlue,
                    unfocusedBorderColor = Color(0xFFD9D9D9),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        "Email",
                        color = Color(0xFF8B8E9A),
                        fontSize = 17.sp
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth().height(62.dp),
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = RoamlyBlue,
                    unfocusedBorderColor = Color(0xFFD9D9D9),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(
                        "Password",
                        color = Color(0xFF8B8E9A),
                        fontSize = 17.sp
                    )
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = RoamlyBlue,
                    unfocusedBorderColor = Color(0xFFD9D9D9),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (uiState is AuthUiState.Error) {
                Text(
                    text = (uiState as AuthUiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = { viewModel.signUp(email, password) },
                enabled = uiState !is AuthUiState.Loading,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RoamlyBlue)
            ) {
                if (uiState is AuthUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = RoamlyBackground
                    )
                } else {
                    Text(
                        "Create Account",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            AuthDivider()
            SocialAuthRow(
                onContinueWithGoogle = onContinueWithGoogle,
                onContinueWithApple = onContinueWithApple
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text("Already have an account? ")
                Text(
                    text = "Sign in",
                    color = RoamlyBlue,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onNavigateToSignIn() }
                )
            }
        }
    }
}
