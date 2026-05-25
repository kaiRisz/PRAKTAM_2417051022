package com.example.praktam_2417051022.ui.screen.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun FavoriteScreen(
    navController: NavController,
    reviews: List<Review>,
    modifier: Modifier = Modifier
) {

    val favoriteReviews = reviews.take(2)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8F5))
    ) {
        Text(
            text = "Anime Favorit Saya",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2B2B2B),
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 16.dp)
        )

        if (favoriteReviews.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Belum ada anime favorit yang ditambahkan",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(favoriteReviews) { review ->
                    ReviewItemHorizontal(review = review, navController = navController)
                }
            }
        }
    }
}