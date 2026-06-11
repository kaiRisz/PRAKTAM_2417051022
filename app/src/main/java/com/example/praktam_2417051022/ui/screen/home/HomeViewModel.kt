package com.example.praktam_2417051022.ui.screen.home

import android.app.Application
<<<<<<< HEAD
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktam_2417051022.data.api.RetrofitClient
import com.example.praktam_2417051022.data.local.AppDatabase
=======
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
>>>>>>> be73391 (bikin fitur CRUD)
import com.example.praktam_2417051022.data.model.Review
import com.example.praktam_2417051022.data.repository.ReviewRepository
import kotlinx.coroutines.launch

<<<<<<< HEAD
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val reviewDao = AppDatabase.getDatabase(application).reviewDao()
=======
// Mengubah dari ViewModel biasa ke AndroidViewModel agar kita bisa mendapatkan akses ke Application Context
class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ReviewRepository()
    private val context = application.applicationContext
>>>>>>> be73391 (bikin fitur CRUD)

    var reviews = mutableStateOf<List<Review>>(listOf())
        private set

    var isLoading = mutableStateOf(false)
        private set

    var isError = mutableStateOf(false)
        private set

    init {
        observeLocalReviews()
        fetchReviewsFromApi()
    }

<<<<<<< HEAD
    private fun observeLocalReviews() {
        viewModelScope.launch {
            reviewDao.getAllReviews().collect { localList ->
                _reviews.value = localList
            }
        }
    }

    fun fetchReviewsFromApi() {
=======
    fun fetchReviews() {
        isLoading.value = true
        isError.value = false
>>>>>>> be73391 (bikin fitur CRUD)
        viewModelScope.launch {
            try {
<<<<<<< HEAD
                val apiResponse = RetrofitClient.instance.getReviews()
                apiResponse.forEach { review ->
                    reviewDao.insertReview(review)
                }
            } catch (e: Exception) {
                if (_reviews.value.isEmpty()) {
                    _isError.value = true
                }
            } finally {
                _isLoading.value = false
=======
                // Mengambil data presisten dari repository
                val data = repository.getAllReviews(context)
                reviews.value = data
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                isError.value = true
            }
        }
    }

    fun deleteReview(nama: String) {
        viewModelScope.launch {
            val isDeleted = repository.deleteReview(context, nama)
            if (isDeleted) {
                // Memicu recomposition UI secara aman tanpa merusak struktur NavController stack
                reviews.value = repository.getAllReviews(context).toList()
>>>>>>> be73391 (bikin fitur CRUD)
            }
        }
    }

    fun insertReview(nama: String, kategori: String, deskripsi: String, imageUrl: String) {
        viewModelScope.launch {
            val newReview = Review(nama = nama, kategori = kategori, deskripsi = deskripsi, imageUrl = imageUrl)
            reviewDao.insertReview(newReview)
        }
    }

    fun updateReview(review: Review) {
        viewModelScope.launch {
            reviewDao.updateReview(review)
        }
    }

    fun deleteReview(review: Review) {
        viewModelScope.launch {
            reviewDao.deleteReview(review)
        }
    }
}