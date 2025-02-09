package com.example.firebaseapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.firebaseapp.data.database_model.FavoriteBook

@Database(entities = [FavoriteBook::class], version = 2, exportSchema = false)
abstract class BooksDatabase: RoomDatabase() {
    abstract fun booksDao(): BooksDao
}