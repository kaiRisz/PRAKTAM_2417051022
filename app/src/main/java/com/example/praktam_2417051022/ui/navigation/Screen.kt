package com.example.praktam_2417051022.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Search : Screen("search", "Search", Icons.Default.Search)
    object Favorite : Screen("favorite", "Favorite", Icons.Default.Favorite)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object Detail : Screen("detail/{nama}", "Detail", Icons.Default.Home) {
        fun createRoute(nama: String) = "detail/$nama"
    }
    object ReviewForm : Screen("review_form/{reviewId}", "Review Form", Icons.Default.Home) {
        fun createRoute(reviewId: String) = "review_form/$reviewId"
    }
}