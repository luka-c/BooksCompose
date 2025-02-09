package com.example.firebaseapp.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseapp.data.BooksRepository
import com.example.firebaseapp.data.database_model.FavoriteBook
import com.example.firebaseapp.model.Book
import com.example.firebaseapp.network.BookApi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val api: BookApi,
    private val savedStateHandle: SavedStateHandle,
    private val booksRepository: BooksRepository
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _book = MutableStateFlow<Book?>(null)
    val book = _book.asStateFlow()

    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)
    val isFavorite = mutableStateOf(false)

    private val bookId: Int = savedStateHandle["bookId"] ?: 0

    init {
        checkIfFavorite()
        getBookDetails()
    }

    fun getBookDetails() {
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = api.getBookById(bookId)

                if (response.isSuccessful) {
                    _book.value = response.body()
                    isLoading.value = false
                }
                else {
                    error.value = "Could not fetch this book"
                    isLoading.value = false
                }
            }
            catch (exception: Exception) {
                error.value = "Could not fetch this book"
                isLoading.value = false
            }
        }
    }

    private fun checkIfFavorite() = viewModelScope.launch {
        booksRepository.getFavorites(auth.currentUser!!.uid).collect { favorites ->
            val indexOf = favorites.indexOfFirst { favorite ->
                favorite.id == bookId
            }
            isFavorite.value = indexOf != -1
        }
    }

    fun addToFavorites() = viewModelScope.launch {
        val bookValue = book.value

        if (bookValue != null) {
            booksRepository.insertFavorite(
                FavoriteBook(
                    id = bookValue.id,
                    title = bookValue.title,
                    imageUrl = bookValue.formats["image/jpeg"] ?: "",
                    user = auth.currentUser!!.uid
                )
            )
        }
    }

    fun removeFromFavorites() = viewModelScope.launch {
        val bookValue = book.value

        if (bookValue != null) {
            booksRepository.deleteFavorite(
                FavoriteBook(
                    id = bookValue.id,
                    title = bookValue.title,
                    imageUrl = bookValue.formats["image/jpg"] ?: "",
                    user = auth.currentUser!!.uid
                )
            )
        }
    }
}