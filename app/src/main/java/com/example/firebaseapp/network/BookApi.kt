package com.example.firebaseapp.network

import com.example.firebaseapp.model.Book
import com.example.firebaseapp.model.BooksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BookApi {

    @GET("books/")
    suspend fun getBooks(
        @Query("page") page: Int,
        @Query("sort") sortBy: String = "popular",
        @Query("ids") listOfIds: String? = null,
        @Query("search") searchTerm: String? = null
    ): Response<BooksResponse>

    @GET("books/{id}")
    suspend fun getBookById(@Path("id") bookId: Int): Response<Book>
}