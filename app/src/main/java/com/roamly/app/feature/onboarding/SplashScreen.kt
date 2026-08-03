package com.roamly.app.feature.onboarding

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roamly.app.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Two independent animatable values — logo fades AND scales in together,
    // each one interpolates on its own timeline via animateTo
    val logoAlpha = remember { Animatable(0f) }
    val logoScale = remember { Animatable(0.8f) }

    LaunchedEffect(key1 = Unit) {
        launch {
            logoAlpha.animateTo(targetValue = 1f, animationSpec = tween(durationMillis = 700))
        }
        launch {
            logoScale.animateTo(targetValue = 1f, animationSpec = tween(durationMillis = 700))
        }
        delay(2200L) // slightly longer than the animation so it doesn't feel cut off
        onTimeout()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.splash_screen),
            contentDescription = null, // decorative background, no screen-reader text needed
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Dark overlay so white logo/text stay readable over a busy photo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.35f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.roamly_logo1),
                contentDescription = "Roamly logo",
                modifier = Modifier
                    .size(1006.dp)
                    .scale(logoScale.value)
                    .alpha(logoAlpha.value)
            )
            Spacer(modifier = Modifier.height(12.dp))
//            Text(
//                text = "Roamly",
//                color = Color.White,
//                fontSize = 32.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.alpha(logoAlpha.value)
//            )
        }
    }
}