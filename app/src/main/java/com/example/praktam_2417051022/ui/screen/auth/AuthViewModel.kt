package com.example.praktam_2417051022.ui.screen.auth

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktam_2417051022.data.api.RetrofitClient
import com.example.praktam_2417051022.data.pref.SessionManager
import kotlinx.coroutines.launch
import org.json.JSONObject

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService = RetrofitClient.instance
    private val sessionManager = SessionManager(application.applicationContext)

    var isLoading = mutableStateOf(false)
    var authResult = mutableStateOf<Result<String>?>(null)

    fun login(user: String, pass: String) {
        if (!sessionManager.checkLogin(user, pass)) {
            authResult.value = Result.failure(Exception("Username atau Password salah/belum terdaftar!"))
            return
        }

        isLoading.value = true
        authResult.value = null
        viewModelScope.launch {
            try {
                // Jembatan ke DummyJSON menggunakan akun emilys
                val response = apiService.loginUser(mapOf("username" to "emilys", "password" to "emilyspass"))
                if (response.isSuccessful) {
                    sessionManager.setLoginSuccess(response.body()?.token ?: "")
                    authResult.value = Result.success("Sukses")
                } else {
                    authResult.value = Result.failure(Exception("Gagal mengambil token API"))
                }
            } catch (e: Exception) {
                // Fallback: Jika internet putus saat login tapi akun lokal benar, tetap loloskan
                sessionManager.setLoginSuccess("dummy_local_token_${System.currentTimeMillis()}")
                authResult.value = Result.success("Sukses (Mode Offline)")
            } finally {
                isLoading.value = false
            }
        }
    }

    fun register(user: String, email: String, pass: String) {
        isLoading.value = true
        authResult.value = null
        viewModelScope.launch {
            try {
                // Mencoba mendaftar ke API DummyJSON
                val response = apiService.registerUser(mapOf("username" to user, "email" to email))
                if (response.isSuccessful) {
                    sessionManager.saveUser(user, email, pass)
                    authResult.value = Result.success("Registrasi Berhasil!")
                } else {
                    // Jika API menolak tapi koneksi aman
                    val errorMsg = response.errorBody()?.string()?.let { JSONObject(it).optString("message") } ?: "Gagal Register"
                    authResult.value = Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                // ANTIBADAI/TIMEOUT FALLBACK:
                // Jika server DummyJSON timeout atau koneksi lambat, langsung simpan ke lokal saja!
                sessionManager.saveUser(user, email, pass)
                authResult.value = Result.success("Registrasi Berhasil! (Mode Lokal Aktif)")
            } finally {
                isLoading.value = false
            }
        }
    }

    fun clearResult() {
        authResult.value = null
    }
}