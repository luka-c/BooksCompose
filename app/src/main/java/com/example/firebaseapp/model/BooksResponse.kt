package com.example.firebaseapp.model

data class BooksResponse(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<Book>
)