package com.example.praktam_2417051022

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktam_2417051022.model.Review
import com.example.praktam_2417051022.model.ReviewSource
import com.example.praktam_2417051022.ui.theme.PRAKTAM_2417051022Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRAKTAM_2417051022Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Modifier padding(innerPadding) penting agar list tidak tertutup bar navigasi
                    DaftarReviewScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun DaftarReviewScreen(modifier: Modifier = Modifier) {
    // LazyColumn akan menampilkan semua item (AOT, Fate, Spiderman) secara otomatis
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp) // Jarak antar kartu
    ) {
        items(ReviewSource.dummyReview) { review ->
            DetailScreen(review = review)
        }
    }
}

@Composable
fun DetailScreen(review: Review) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            // Gambar di bagian atas kartu
            Image(
                painter = painterResource(id = review.imageRes),
                contentDescription = review.nama,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )

            // Konten teks di bawah gambar
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = review.nama, // Menampilkan AOT, Fate, atau Spiderman
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = review.kategori,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Deskripsi: ${review.deskripsi}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Tombol di bagian bawah seperti di contoh "Pesan Sekarang"
                Button(
                    onClick = { /* Aksi Read More */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Read More")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTampilan() {
    PRAKTAM_2417051022Theme {
        DaftarReviewScreen()
    }
}
