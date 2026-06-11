package com.example.praktam_2417051022.ui.screen.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.praktam_2417051022.data.model.Review
import com.example.praktam_2417051022.ui.components.ReviewItemHorizontal

@Composable
fun FavoriteScreen(
    navController: NavController,
    reviews: List<Review>,
    modifier: Modifier = Modifier
) {
    // Mengambil data yang memiliki rating tinggi atau disimulasikan sebagai favorit
    val favoriteReviews = reviews.filter { it.rating >= 4.0 }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (favoriteReviews.isEmpty()) {
            Text(
                text = "Belum ada anime favorit",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(favoriteReviews) { review ->
                    ReviewItemHorizontal(
                        review = review,
                        navController = navController,
                        onDeleteClick = {
                            // Kosong karena tidak ada aksi hapus langsung dari halaman favorit
                        }
                    )
                }
            }
        }
    }
}