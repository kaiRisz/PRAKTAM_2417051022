package com.example.praktam_2417051022.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktam_2417051022.data.model.Review
import com.example.praktam_2417051022.data.repository.ReviewRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = ReviewRepository()

    var reviews by mutableStateOf<List<Review>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var isError by mutableStateOf(false)
        private set

    init {
        fetchReviews()
    }

    fun fetchReviews() {
        viewModelScope.launch {
            isLoading = true
            isError = false
            try {
                val result = repository.getReviews()
                reviews = result
                isLoading = false
            } catch (e: Exception) {
                isLoading = false
                isError = true
            }
        }
    }
}