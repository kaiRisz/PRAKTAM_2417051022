package com.example.praktam_2417051022.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.praktam_2417051022.data.model.Review
import com.example.praktam_2417051022.ui.screen.detail.DetailScreen
import com.example.praktam_2417051022.ui.screen.home.HomeScreen
import com.example.praktam_2417051022.ui.screen.home.ReviewFormScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    reviews: List<Review>,
    onReviewsLoaded: (List<Review>) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                onReviewsLoaded = onReviewsLoaded
            )
        }
        composable(Screen.Search.route) {}
        composable(Screen.Favorite.route) {}
        composable(Screen.Profile.route) {}

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("nama") { type = NavType.StringType })
        ) { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            DetailScreen(navController = navController, namaAnime = nama)
        }

        composable(
            route = Screen.ReviewForm.route,
            arguments = listOf(navArgument("reviewId") { type = NavType.StringType })
        ) { backStackEntry ->
            val reviewId = backStackEntry.arguments?.getString("reviewId") ?: "0"
            ReviewFormScreen(navController = navController, reviewId = reviewId)
        }
    }
}