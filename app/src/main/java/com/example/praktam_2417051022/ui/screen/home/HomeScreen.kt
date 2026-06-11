package com.example.praktam_2417051022.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.praktam_2417051022.data.model.Review
import com.example.praktam_2417051022.ui.components.ReviewItemHorizontal
import com.example.praktam_2417051022.ui.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    onReviewsLoaded: (List<Review>) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val reviews = viewModel.reviews.value
    val isLoading = viewModel.isLoading.value
    val isError = viewModel.isError.value

    var showDeleteDialog by remember { mutableStateOf(false) }
    var reviewToDelete by remember { mutableStateOf<Review?>(null) }

    LaunchedEffect(reviews) {
        onReviewsLoaded(reviews)
    }

    if (showDeleteDialog && reviewToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(text = "Hapus Review") },
            text = { Text(text = "Apakah anda yakin ingin hapus review untuk '${reviewToDelete?.nama}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        reviewToDelete?.let { viewModel.deleteReview(it.nama) }
                        showDeleteDialog = false
                        reviewToDelete = null
                    }
                ) {
                    Text("Hapus", color = Color(0xFF8B1E22))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Batal", color = Color.Gray)
                }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.ReviewForm.createRoute("0")) },
                containerColor = Color(0xFF8B1E22),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Review")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color(0xFF8B1E22))
            } else if (isError) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Gagal memuat data", color = Color.Red)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.fetchReviews() }) {
                        Text(text = "Coba Lagi")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(reviews) { review ->
                        ReviewItemHorizontal(
                            review = review,
                            navController = navController,
                            onDeleteClick = {
                                reviewToDelete = review
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }
    }
}