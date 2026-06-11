package com.example.praktam_2417051022.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.praktam_2417051022.data.model.Review
import com.example.praktam_2417051022.data.repository.ReviewRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewFormScreen(navController: NavController, reviewId: String) {
    val context = LocalContext.current
    val repository = remember { ReviewRepository() }

    // Inisialisasi data repository terlebih dahulu agar sinkron dengan SharedPreferences
    LaunchedEffect(Unit) {
        repository.initialize(context)
    }

    val isEditMode = reviewId != "0"
    val existingReview = if (isEditMode) repository.getReviewByNama(reviewId) else null

    var nama by remember { mutableStateOf(existingReview?.nama ?: "") }
    var kategori by remember { mutableStateOf(existingReview?.kategori ?: "") }
    var deskripsi by remember { mutableStateOf(existingReview?.deskripsi ?: "") }
    var imageUrl by remember { mutableStateOf(existingReview?.imageUrl ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Review Anime" else "Tambah Review Anime") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFFF8F5))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nama,
                onValueChange = { nama = it },
                label = { Text("Nama Anime") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isEditMode // Nama tidak boleh diubah jika dalam mode edit (sebagai key)
            )
            OutlinedTextField(
                value = kategori,
                onValueChange = { kategori = it },
                label = { Text("Kategori / Genre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("URL Gambar Sampul") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = deskripsi,
                onValueChange = { deskripsi = it },
                label = { Text("Sinopsis / Deskripsi Review") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4
            )
            Button(
                onClick = {
                    val review = Review(
                        id = existingReview?.id ?: System.currentTimeMillis().toString(),
                        nama = nama,
                        kategori = kategori,
                        deskripsi = deskripsi,
                        imageUrl = imageUrl,
                        rating = existingReview?.rating ?: 5.0
                    )
                    if (isEditMode) {
                        repository.updateReview(context, reviewId, review)
                    } else {
                        repository.createReview(context, review)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B1E22))
            ) {
                Text("Simpan Data", color = Color.White)
            }
        }
    }
}