package com.roamly.app.feature.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roamly.app.navigation.BottomNavScreen
import com.roamly.app.ui.theme.RoamlyBlue
import androidx.compose.runtime.getValue

@Composable
fun FloatingBottomNavBar(
    items: List<BottomNavScreen>,
    isSelected: (BottomNavScreen) -> Boolean,
    onItemClick: (BottomNavScreen) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 12.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    NavPillItem(
                        item = item,
                        selected = isSelected(item),
                        onClick = { onItemClick(item) }
                    )
                }
            }
        }
    }
}

@Composable
private fun NavPillItem(
    item: BottomNavScreen,
    selected: Boolean,
    onClick: () -> Unit
) {
    // Animates smoothly between transparent and RoamlyBlue instead of snapping instantly
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) RoamlyBlue else Color.Transparent,
        animationSpec = tween(durationMillis = 250),
        label = "navPillColor"
    )

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = if (selected) 16.dp else 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            tint = if (selected) Color.White else Color.Gray,
            modifier = Modifier.size(22.dp)
        )
        // Label only appears next to the icon when selected — the pill "expands"
        if (selected) {
            Spacer(modifier = Modifier.width(6.dp))
            Text(item.label, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}