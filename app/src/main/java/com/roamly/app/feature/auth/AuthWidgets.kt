package com.roamly.app.feature.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roamly.app.R

@Composable
fun AuthDivider() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f))
        Text(
            text = "  or continue with  ",
            color = Color.Gray,
            fontSize = 13.sp
        )
        HorizontalDivider(modifier = Modifier.weight(1f))
    }
}

@Composable
fun SocialAuthRow(
    onContinueWithGoogle: () -> Unit,
    onContinueWithApple: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SocialIconButton(
            iconRes = R.drawable.google_logo,
            backgroundColor = Color.White,
            hasBorder = true,
            onClick = onContinueWithGoogle,
            modifier = Modifier.weight(1f)
        )
        SocialIconButton(
            iconRes = R.drawable.apple_logo,
            backgroundColor = Color.White,
            hasBorder = true,
            onClick = onContinueWithApple,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SocialIconButton(
    iconRes: Int,
    backgroundColor: Color,
    hasBorder: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(backgroundColor)
            .then(
                if (hasBorder) Modifier.border(1.dp, Color.LightGray, RoundedCornerShape(26.dp))
                else Modifier
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(22.dp)
        )
    }
}