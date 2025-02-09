package com.example.firebaseapp.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseapp.data.BooksRepository
import com.example.firebaseapp.data.database_model.FavoriteBook
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val booksRepository: BooksRepository
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _favoriteBooks = MutableStateFlow<List<FavoriteBook>>(emptyList())
    val favoriteBooks = _favoriteBooks.asStateFlow()

    init {
        getFavorites()
    }

    private fun getFavorites() = viewModelScope.launch {
        booksRepository.getFavorites(auth.currentUser!!.uid).collect {
            _favoriteBooks.value = it
        }
    }

    fun deleteAllFavorites() = viewModelScope.launch {
        booksRepository.deleteAllFavorites()
    }

    fun deleteFavorite(favoriteBook: FavoriteBook) = viewModelScope.launch {
        booksRepository.deleteFavorite(favoriteBook)
    }
}