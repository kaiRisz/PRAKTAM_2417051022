package com.example.praktam_2417051022.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.praktam_2417051022.data.model.Review
import com.example.praktam_2417051022.ui.screen.auth.LoginScreen
import com.example.praktam_2417051022.ui.screen.auth.RegisterScreen
import com.example.praktam_2417051022.ui.screen.detail.DetailScreen
import com.example.praktam_2417051022.ui.screen.favorite.FavoriteScreen
import com.example.praktam_2417051022.ui.screen.home.HomeScreen
import com.example.praktam_2417051022.ui.screen.profile.ProfileScreen
import com.example.praktam_2417051022.ui.screen.search.SearchScreen
import com.example.praktam_2417051022.ui.screen.splash.SplashScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    reviews: List<Review>,
    onReviewsLoaded: (List<Review>) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("register") {
            RegisterScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                modifier = modifier,
                onReviewsLoaded = onReviewsLoaded
            )
        }
        composable(Screen.Search.route) {
            SearchScreen(
                navController = navController,
                reviews = reviews,
                modifier = modifier
            )
        }
        composable(Screen.Favorite.route) {
            FavoriteScreen(
                navController = navController,
                reviews = reviews,
                modifier = modifier
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(modifier = modifier)
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