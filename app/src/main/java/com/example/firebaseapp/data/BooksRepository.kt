package com.example.firebaseapp.data

import com.example.firebaseapp.data.database_model.FavoriteBook
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BooksRepository @Inject constructor(private val booksDao: BooksDao) {

    fun getFavorites(user: String): Flow<List<FavoriteBook>> = booksDao.getFavorites(user)

    suspend fun insertFavorite(favorite: FavoriteBook) = booksDao.insertFavorite(favorite)

    suspend fun deleteAllFavorites() = booksDao.deleteAllFavorites()

    suspend fun deleteFavorite(favorite: FavoriteBook) = booksDao.deleteFavorite(favorite)
}