package com.example.praktam_2417051022.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.praktam_2417051022.data.model.Review
import com.example.praktam_2417051022.ui.components.ReviewItemHorizontal
import com.example.praktam_2417051022.ui.components.ReviewRowItem

@Composable
fun HomeScreen(
    navController: NavController,
    onReviewsLoaded: (List<Review>) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val reviews by viewModel.reviews
    val isLoading by viewModel.isLoading
    val isError by viewModel.isError

    LaunchedEffect(reviews) {
        if (reviews.isNotEmpty()) {
            onReviewsLoaded(reviews)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8F5)),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color(0xFF8B1E22))
        } else if (isError) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Gagal memuat data", color = Color.Red, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.fetchReviews() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B1E22))
                ) {
                    Text("Coba Lagi")
                }
            }
        } else {
            ContentView(reviews = reviews, navController = navController)
        }
    }
}

@Composable
fun ContentView(reviews: List<Review>, navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hi, Khaila 👋",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2B2B2B)
                    )
                    Text(
                        text = "Temukan & baca review anime favoritmu!",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .background(Color.White, shape = CircleShape)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = Color(0xFF2B2B2B)
                    )
                }
            }
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                placeholder = { Text("Cari anime, genre, atau studio...", color = Color.Gray, fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                singleLine = true,
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF8B1E22).copy(alpha = 0.5f),
                    unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f)
                )
            )
        }

        item {
            Column(modifier = Modifier.padding(bottom = 24.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Trending Now", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2B2B2B))
                        Text(" 🔥", fontSize = 16.sp)
                    }
                    Text(
                        text = "Lihat semua",
                        fontSize = 12.sp,
                        color = Color(0xFF8B1E22),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable {}
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(reviews) { review ->
                        ReviewRowItem(review = review, navController = navController)
                    }
                }
            }
        }

        item {
            Text(
                text = "Daftar Review Anime",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2B2B2B),
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
            )
        }

        items(reviews) { review ->
            Box(modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 14.dp)) {
                ReviewItemHorizontal(review = review, navController = navController)
            }
        }
    }
}