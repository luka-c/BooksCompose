package com.example.firebaseapp.screens.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseapp.model.Book
import com.example.firebaseapp.network.BookApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val api: BookApi) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()

    val isLoading = mutableStateOf(true)
    val error = mutableStateOf<String?>(null)

    private val page = mutableIntStateOf(1)
    private val sortBy = mutableStateOf("popular")

    init {
        getBooks()
    }

    fun getBooks(sort: String = sortBy.value) {
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = api.getBooks(page = 1, sortBy = sort)

                if (response.isSuccessful && response.body() != null) {
                    _books.value = response.body()!!.results
                    error.value = null
                    page.intValue = 2
                }
                else {
                    error.value = "Could not fetch books"
                }

                isLoading.value = false
            }
            catch (e: Exception) {
                error.value = "Could not fetch books"
                isLoading.value = false
            }
        }
    }

    fun loadMoreBooks() {
        viewModelScope.launch {
            try {
                val response = api.getBooks(
                    page = page.intValue,
                    sortBy = sortBy.value
                )

                if (response.isSuccessful && response.body() != null) {
                    _books.value += response.body()!!.results
                    error.value = null
                    page.intValue++
                }
                else {
                    error.value = "Could not fetch books"
                }
            } catch (e: Exception) {
                error.value = "Could not fetch books"
                isLoading.value = false
            }
        }
    }
}