package com.example.firebaseapp.data.database_model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class FavoriteBook(
    @PrimaryKey
    @ColumnInfo("idBook")
    val id: Int,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("imageUrl")
    val imageUrl: String,

    @ColumnInfo("user")
    val user: String
)
