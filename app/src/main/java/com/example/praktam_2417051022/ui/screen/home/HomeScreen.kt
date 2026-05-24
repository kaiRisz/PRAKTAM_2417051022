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
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    onReviewsLoaded: (List<Review>) -> Unit
) {
    if (!viewModel.isLoading && !viewModel.isError) {
        onReviewsLoaded(viewModel.reviews)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8F5))
    ) {
        when {
            viewModel.isLoading -> LoadingView()
            viewModel.isError || viewModel.reviews.isEmpty() -> ErrorView(onRetry = { viewModel.fetchReviews() })
            else -> ContentView(viewModel.reviews, navController)
        }
    }
}

@Composable
fun LoadingView() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color(0xFF8B1E22))
    }
}

@Composable
fun ErrorView(onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Gagal Memuat Data", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF8B1E22))
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B1E22))) {
                Text("Coba Lagi", color = Color.White)
            }
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
                    onClick = { /* Handle Notif */ },
                    modifier = Modifier
                        .background(Color.White, shape = CircleShape)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifikasi",
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
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) },
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
                        modifier = Modifier.clickable { /* See All */ }
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