package com.example.praktam_2417051022.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktam_2417051022.data.api.RetrofitClient
import com.example.praktam_2417051022.data.model.Review
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _reviews = mutableStateOf<List<Review>>(emptyList())
    val reviews: State<List<Review>> = _reviews

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isError = mutableStateOf(false)
    val isError: State<Boolean> = _isError

    init {
        fetchReviews()
    }

    fun fetchReviews() {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false
            try {
                val response = RetrofitClient.instance.getReviews()
                _reviews.value = response
            } catch (e: Exception) {
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}