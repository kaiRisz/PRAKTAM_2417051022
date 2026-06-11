package com.example.praktam_2417051022.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val authResult = viewModel.authResult.value
    val isLoading = viewModel.isLoading.value

    LaunchedEffect(authResult) {
        authResult?.let {
            if (it.isSuccess) {
                Toast.makeText(context, it.getOrNull(), Toast.LENGTH_SHORT).show()
                onLoginSuccess()
            } else {
                Toast.makeText(context, it.exceptionOrNull()?.message, Toast.LENGTH_SHORT).show()
            }
            viewModel.clearResult()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Selamat Datang Kembali", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            Text("Silakan masuk menggunakan akun DummyJSON", color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (isLoading) {
                CircularProgressIndicator(color = Color(0xFF8B1E22))
            } else {
                Button(
                    onClick = { viewModel.login(username, password) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B1E22))
                ) {
                    Text("Masuk", color = Color.White)
                }
            }

            Text(
                text = "Belum punya akun? Daftar disini",
                color = Color(0xFF8B1E22),
                modifier = Modifier.clickable { onNavigateToRegister() },
                fontWeight = FontWeight.Medium
            )
        }
    }
}