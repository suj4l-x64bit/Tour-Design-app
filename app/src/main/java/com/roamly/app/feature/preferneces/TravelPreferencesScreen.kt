package com.roamly.app.feature.preferences

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.roamly.app.ui.theme.RoamlyBlue

private val travelStyleOptions = listOf("Adventure", "Luxury", "Food", "Culture", "Beach", "Family")
private val paceOptions = listOf("Relaxed", "Balanced", "Fast-paced")

private data class DestinationOption(val name: String, val imageUrl: String)

private val destinationOptions = listOf(
    DestinationOption("Bali", "https://picsum.photos/seed/bali/300/300"),
    DestinationOption("Paris", "https://picsum.photos/seed/paris/300/300"),
    DestinationOption("Iceland", "https://picsum.photos/seed/iceland/300/300"),
    DestinationOption("Japan", "https://picsum.photos/seed/japan/300/300")
)

@Composable
fun TravelPreferencesScreen(
    onDone: () -> Unit,
    viewModel: TravelPreferencesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState is PreferencesUiState.Success) {
            onDone()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Decorative for now — becomes a real step-tracker once this form
        // is split across multiple screens; harmless as a single static bar today
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LinearProgressIndicator(
                progress = { 0.3f },
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = RoamlyBlue,
                trackColor = Color(0xFFE5E7EB)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Skip",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.clickable { onDone() }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Tell us your travel style", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        Text(
            "We'll personalize every trip to fit you",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text("I love to travel for", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
        FlowRowChips(
            options = travelStyleOptions,
            isSelected = { it in viewModel.selectedStyles },
            onToggle = { viewModel.toggleStyle(it) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Favorite destinations", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(destinationOptions, key = { it.name }) { destination ->
                DestinationThumbnail(
                    destination = destination,
                    isSelected = destination.name in viewModel.selectedDestinations,
                    onClick = { viewModel.toggleDestination(destination.name) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF7F8FA))
                .padding(16.dp)
        ) {
            Text("Budget range (per person)", fontWeight = FontWeight.SemiBold)
            Text(
                "$${viewModel.budgetRange.start.toInt()} - $${viewModel.budgetRange.endInclusive.toInt()}",
                color = RoamlyBlue,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
            RangeSlider(
                value = viewModel.budgetRange,
                onValueChange = { viewModel.updateBudgetRange(it) },
                valueRange = 500f..5000f,
                colors = SliderDefaults.colors(thumbColor = RoamlyBlue, activeTrackColor = RoamlyBlue)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Travel pace", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            paceOptions.forEach { pace ->
                FilterChip(
                    selected = viewModel.travelPace == pace,
                    onClick = { viewModel.updateTravelPace(pace) },
                    label = { Text(pace) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = RoamlyBlue,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        if (uiState is PreferencesUiState.Error) {
            Text(
                text = (uiState as PreferencesUiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = { viewModel.savePreferences() },
            enabled = uiState !is PreferencesUiState.Loading,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RoamlyBlue)
        ) {
            if (uiState is PreferencesUiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
            } else {
                Text("Next")
            }
        }
    }
}

@Composable
private fun DestinationThumbnail(
    destination: DestinationOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(16.dp))
                .then(
                    if (isSelected) Modifier.border(2.dp, RoamlyBlue, RoundedCornerShape(16.dp))
                    else Modifier
                )
        ) {
            AsyncImage(
                model = destination.imageUrl,
                contentDescription = destination.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(RoamlyBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✓", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(destination.name, fontSize = 12.sp)
    }
}

@Composable
private fun FlowRowChips(
    options: List<String>,
    isSelected: (String) -> Boolean,
    onToggle: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        options.chunked(3).forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rowItems.forEach { option ->
                    FilterChip(
                        selected = isSelected(option),
                        onClick = { onToggle(option) },
                        label = { Text(option) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = RoamlyBlue,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }
    }
}