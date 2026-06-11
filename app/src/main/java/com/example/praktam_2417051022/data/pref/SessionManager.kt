package com.example.praktam_2417051022.data.pref

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USERNAME = "active_username"
        private const val KEY_EMAIL = "active_email"
        private const val KEY_PASSWORD = "saved_password"
        private const val KEY_LOGIN_TIME = "login_time"
        private const val SESSION_DURATION = 10 * 60 * 1000 // 10 Menit
    }

    // Digunakan saat Register
    fun saveUser(username: String, email: String, password: String) {
        prefs.edit().apply {
            putString(KEY_USERNAME, username)
            putString(KEY_EMAIL, email)
            putString(KEY_PASSWORD, password)
            apply()
        }
    }

    // Validasi Login Lokal
    fun checkLogin(username: String, password: String): Boolean {
        val savedUser = prefs.getString(KEY_USERNAME, "")
        val savedPass = prefs.getString(KEY_PASSWORD, "")
        return username == savedUser && password == savedPass
    }

    // Set Sesi Aktif
    fun setLoginSuccess(token: String) {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_TOKEN, token)
            putLong(KEY_LOGIN_TIME, System.currentTimeMillis())
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        val loggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        if (!loggedIn) return false

        // Cek kedaluwarsa
        val time = prefs.getLong(KEY_LOGIN_TIME, 0L)
        if (System.currentTimeMillis() - time > SESSION_DURATION) {
            logout()
            return false
        }
        return true
    }

    fun getUsername(): String = prefs.getString(KEY_USERNAME, "Pengguna") ?: "Pengguna"
    fun getEmail(): String = prefs.getString(KEY_EMAIL, "email@example.com") ?: "email@example.com"

    fun logout() {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, false)
            putString(KEY_TOKEN, null)
            putLong(KEY_LOGIN_TIME, 0L)
            apply()
        }
    }
}