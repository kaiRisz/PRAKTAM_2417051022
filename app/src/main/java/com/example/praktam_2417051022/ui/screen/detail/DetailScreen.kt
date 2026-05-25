package com.example.praktam_2417051022.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.praktam_2417051022.data.model.Review

@Composable
fun DetailScreen(review: Review, navController: NavController, isFullScreen: Boolean = false) {
    val scrollState = rememberScrollState()
    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).verticalScroll(scrollState)) {
        Box {
            AsyncImage(
                model = review.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(320.dp),
                contentScale = ContentScale.Crop
            )
            if (isFullScreen) {
                SmallFloatingActionButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(16.dp),
                    containerColor = Color.White.copy(alpha = 0.7f),
                    shape = CircleShape
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        }
        Column(modifier = Modifier.padding(20.dp)) {
            Text(review.nama, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(review.kategori, Modifier.padding(horizontal = 12.dp, vertical = 4.dp), style = MaterialTheme.typography.labelLarge)
            }
            HorizontalDivider(Modifier.padding(vertical = 16.dp))
            Text("Sinopsis & Review", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(review.deskripsi, style = MaterialTheme.typography.bodyLarge, lineHeight = 24.sp)
            Spacer(Modifier.height(32.dp))
            Button(onClick = { navController.popBackStack() }, Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(12.dp)) {
                Text("Kembali ke Beranda")
            }
        }
    }
}