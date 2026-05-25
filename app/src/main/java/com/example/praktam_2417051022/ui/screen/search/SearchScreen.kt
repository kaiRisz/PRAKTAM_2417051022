package com.example.praktam_2417051022.ui.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.praktam_2417051022.data.model.Review
import com.example.praktam_2417051022.ui.components.ReviewItemHorizontal

@Composable
fun SearchScreen(
    navController: NavController,
    reviews: List<Review>,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }

    val filteredReviews = reviews.filter {
        it.nama.contains(query, ignoreCase = true) || it.kategori.contains(query, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8F5))
    ) {
        Text(
            text = "Pencarian Anime",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2B2B2B),
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 16.dp)
        )

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 16.dp),
            placeholder = { Text("Ketik nama anime atau genre...", color = Color.Gray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(28.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFF8B1E22),
                unfocusedBorderColor = Color.LightGray
            )
        )

        if (filteredReviews.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Anime yang kamu cari tidak ditemukan",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(filteredReviews) { review ->
                    ReviewItemHorizontal(review = review, navController = navController)
                }
            }
        }
    }
}