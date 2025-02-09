package com.example.firebaseapp.screens.search

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
class SearchViewModel @Inject constructor(
    private val api: BookApi
): ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()

    val error = mutableStateOf<String?>(null)
    val isLoading = mutableStateOf(false)
    private val page = mutableIntStateOf(1)

    fun search(input: String) = viewModelScope.launch {
        try {
            isLoading.value = true
            val response = api.getBooks(
                page = 1,
                sortBy = "ascending",
                searchTerm = input
            )

            if (response.isSuccessful && response.body() != null) {
                _books.value = response.body()!!.results
                isLoading.value = false
                error.value = null
            }
            else {
                error.value = "Could not find any books"
                isLoading.value = false
            }
        }
        catch (exception: Exception) {
            error.value = "Something went wrong"
            isLoading.value = false
        }
    }

    fun loadMoreBooks() {
        viewModelScope.launch {
            try {
                val response = api.getBooks(
                    page = page.intValue,
                    sortBy = "ascending"
                )

                if (response.isSuccessful && response.body() != null) {
                    _books.value += response.body()!!.results
                    error.value = null
                    page.intValue++
                }
                else {
                    error.value = "Could not find any books"
                }
            } catch (e: Exception) {
                error.value = "Could not find any books"
                isLoading.value = false
            }
        }
    }
}