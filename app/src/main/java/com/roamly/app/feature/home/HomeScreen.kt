package com.roamly.app.feature.home

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
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
import coil.compose.AsyncImage
import com.roamly.app.ui.theme.RoamlyBlue

@Composable
fun HomeScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val featured = sampleDestinations.first()
    val recommended = sampleDestinations.drop(1)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        // Full-width sections use span = maxLineSpan to occupy both grid columns
        item(span = { GridItemSpan(maxLineSpan) }) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Where do you want to go?", color = RoamlyBlue, fontWeight = FontWeight.Bold) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = RoamlyBlue) },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
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
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categoryOptions) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = if (selectedCategory == category) null else category },
                        label = { Text(category, color = Color.Black) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = RoamlyBlue,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            FeaturedCard(featured)
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recommended for you", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("See all", color = RoamlyBlue, fontSize = 14.sp)
            }
        }

        items(recommended, key = { it.name }) { destination ->
            RecommendedCard(destination)
        }
    }
}

@Composable
private fun FeaturedCard(destination: Destination) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        AsyncImage(
            model = destination.imageUrl,
            contentDescription = "${destination.name}, ${destination.location}",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Text(destination.name, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(destination.location, color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
        }
        RatingBadge(
            rating = destination.rating,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        )
    }
}

@Composable
private fun RecommendedCard(destination: Destination) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(16.dp))

        ) {
            AsyncImage(
                model = destination.imageUrl,
                contentDescription = "${destination.name}, ${destination.location}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            RatingBadge(
                rating = destination.rating,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(destination.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        Text(
            "$${destination.priceFrom} · ${destination.days} Days",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun RatingBadge(rating: Float, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.Star,
            contentDescription = null,
            tint = Color(0xFFFFC107),
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(rating.toString(), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}