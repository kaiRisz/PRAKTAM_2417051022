package com.example.praktam_2417051022.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.praktam_2417051022.data.model.Review
import com.example.praktam_2417051022.data.pref.SessionManager
import com.example.praktam_2417051022.ui.screen.auth.LoginScreen
import com.example.praktam_2417051022.ui.screen.auth.RegisterScreen
import com.example.praktam_2417051022.ui.screen.detail.DetailScreen
import com.example.praktam_2417051022.ui.screen.home.HomeScreen
import com.example.praktam_2417051022.ui.screen.home.ReviewFormScreen
import com.example.praktam_2417051022.ui.screen.profile.ProfileScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    reviews: List<Review>,
    onReviewsLoaded: (List<Review>) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val startDestination = if (sessionManager.isLoggedIn()) Screen.Home.route else Screen.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Screen.Login.route) },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(navController = navController, onReviewsLoaded = onReviewsLoaded)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

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