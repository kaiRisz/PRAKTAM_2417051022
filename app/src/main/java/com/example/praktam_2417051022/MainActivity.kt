package com.example.praktam_2417051022

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import coil.compose.AsyncImage
import com.example.praktam_2417051022.model.Review
import com.example.praktam_2417051022.network.RetrofitClient
import com.example.praktam_2417051022.ui.theme.PRAKTAM_2417051022Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRAKTAM_2417051022Theme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(navController = navController, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    var reviews by remember { mutableStateOf<List<Review>>(emptyList()) }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            DaftarReviewScreen(navController = navController, modifier = modifier) { fetched ->
                reviews = fetched
            }
        }
        composable("detail/{nama}") { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama")
            val review = reviews.find { it.nama == nama }
            if (review != null) {
                DetailScreen(review = review, navController = navController, isFullScreen = true)
            }
        }
    }
}

@Composable
fun DaftarReviewScreen(navController: NavController, modifier: Modifier = Modifier, onReviewsLoaded: (List<Review>) -> Unit) {
    var reviews by remember { mutableStateOf<List<Review>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val result = RetrofitClient.instance.getReviews()
            reviews = result
            onReviewsLoaded(result)
            isLoading = false
            isError = false
        } catch (e: Exception) {
            isLoading = false
            isError = true
        }
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (isError || reviews.isEmpty()) {
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
                Text(
                    text = "Pastikan koneksi internet Anda menyala",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    } else {
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
}

@Composable
fun ReviewRowItem(review: Review, navController: NavController) {
    Card(
        modifier = Modifier.width(180.dp).clickable { navController.navigate("detail/${review.nama}") },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = review.imageUrl,
                contentDescription = review.nama,
                placeholder = painterResource(id = R.drawable.aot),
                error = painterResource(id = R.drawable.aot),
                modifier = Modifier.fillMaxWidth().height(120.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(review.nama, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(review.kategori, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
fun ReviewItemHorizontal(review: Review, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { navController.navigate("detail/${review.nama}") },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = review.imageUrl,
                contentDescription = review.nama,
                placeholder = painterResource(id = R.drawable.aot),
                error = painterResource(id = R.drawable.aot),
                modifier = Modifier.size(110.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(review.nama, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(review.kategori, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                Text(review.deskripsi, style = MaterialTheme.typography.bodySmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
fun DetailScreen(review: Review, navController: NavController, isFullScreen: Boolean = false) {
    val scrollState = rememberScrollState()
    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).verticalScroll(scrollState)) {
        Box {
            AsyncImage(
                model = review.imageUrl,
                contentDescription = review.nama,
                placeholder = painterResource(id = R.drawable.aot),
                error = painterResource(id = R.drawable.aot),
                modifier = Modifier.fillMaxWidth().height(320.dp),
                contentScale = ContentScale.Crop
            )
            if (isFullScreen) {
                SmallFloatingActionButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(16.dp),
                    containerColor = Color.White.copy(alpha = 0.7f),
                    shape = CircleShape
                ) { Icon(Icons.Default.ArrowBack, "Back") }
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

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
        )
    }
}