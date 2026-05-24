package com.example.praktam_2417051022

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.praktam_2417051022.ui.navigation.AppNavigation
import com.example.praktam_2417051022.ui.navigation.Screen
import com.example.praktam_2417051022.ui.theme.PRAKTAM_2417051022Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRAKTAM_2417051022Theme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Daftar halaman yang BOLEH menampilkan Bottom Navigation Bar
                val navigationItems = listOf(
                    Screen.Home,
                    Screen.Search,
                    Screen.Favorite,
                    Screen.Profile
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        // Hanya tampilkan bottom bar jika route saat ini ada di daftar navigationItems
                        if (navigationItems.any { it.route == currentRoute }) {
                            NavigationBar(
                                containerColor = Color(0xFFFFF8F5) // Warna cream dari desainmu
                            ) {
                                navigationItems.forEach { item ->
                                    val selected = currentRoute == item.route
                                    NavigationBarItem(
                                        selected = selected,
                                        onClick = {
                                            if (!selected) {
                                                navController.navigate(item.route) {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                        },
                                        icon = { Icon(item.icon, contentDescription = item.title) },
                                        label = { Text(item.title) },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = Color.White,
                                            selectedTextColor = Color(0xFF8B1E22), // Warna teks aktif marun
                                            indicatorColor = Color(0xFF8B1E22), // Background icon aktif marun
                                            unselectedIconColor = Color.Gray,
                                            unselectedTextColor = Color.Gray
                                        )
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}