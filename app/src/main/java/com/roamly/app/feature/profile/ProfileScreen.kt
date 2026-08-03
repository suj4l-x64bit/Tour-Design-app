package com.roamly.app.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roamly.app.ui.theme.RoamlyBlue

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(RoamlyBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = viewModel.displayName.take(1).uppercase(),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(viewModel.displayName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(viewModel.email, color = Color.Gray, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF7F8FA))
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileStat(label = "Trips", value = "0")
            ProfileStat(label = "Countries", value = "0")
            ProfileStat(label = "Memories", value = "0")
        }

        Spacer(modifier = Modifier.height(24.dp))

        ProfileMenuItem(icon = Icons.Default.Notifications, label = "Notifications") {}
        ProfileMenuItem(icon = Icons.Default.Settings, label = "Preferences") {}
        ProfileMenuItem(icon = Icons.Default.AccountCircle, label = "Account") {}
        ProfileMenuItem(icon = Icons.Default.Info, label = "Help & Support") {}

        Spacer(modifier = Modifier.weight(1f))

        ProfileMenuItem(
            icon = Icons.Default.ExitToApp,
            label = "Log out",
            tint = MaterialTheme.colorScheme.error
        ) {
            viewModel.signOut()
            onLogout()
        }
    }
}

@Composable
private fun ProfileStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    label: String,
    tint: Color = LocalContentColor.current,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = label, tint = tint)
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, fontSize = 16.sp, color = tint)
    }
    HorizontalDivider()
}