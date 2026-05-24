package com.example.praktam_2417051022.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.praktam_2417051022.data.model.Review
import com.example.praktam_2417051022.ui.screen.detail.DetailScreen
import com.example.praktam_2417051022.ui.screen.home.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    var reviews by remember { mutableStateOf<List<Review>>(emptyList()) }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController, modifier = modifier) { fetched ->
                reviews = fetched
            }
        }
        composable("detail/{nama}") { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama")
            val review = reviews.find { it.nama == nama }
            review?.let {
                DetailScreen(review = it, navController = navController, isFullScreen = true)
            }
        }
    }
}