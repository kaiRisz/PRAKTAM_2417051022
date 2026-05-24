package com.example.praktam_2417051022.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

    when {
        viewModel.isLoading -> LoadingView()
        viewModel.isError || viewModel.reviews.isEmpty() -> ErrorView(onRetry = { viewModel.fetchReviews() })
        else -> ContentView(viewModel.reviews, navController, modifier)
    }
}

@Composable
fun LoadingView() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Gagal Memuat Data",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text("Coba Lagi")
            }
        }
    }
}

@Composable
fun ContentView(reviews: List<Review>, navController: NavController, modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().statusBarsPadding(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text("Rekomendasi Populer", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            LazyRow(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(reviews) { ReviewRowItem(it, navController) }
            }
            Spacer(Modifier.height(32.dp))
            Text("Daftar Review Anime", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
        }
        items(reviews) { ReviewItemHorizontal(it, navController) }
    }
}