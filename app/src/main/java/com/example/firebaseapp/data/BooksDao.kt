package com.example.firebaseapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.firebaseapp.data.database_model.FavoriteBook
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Query("SELECT * FROM favorite_books WHERE user =:user")
    fun getFavorites(user: String) : Flow<List<FavoriteBook>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteBook)

    @Query("DELETE FROM favorite_books")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteBook)
}