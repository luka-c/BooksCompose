package com.example.firebaseapp.utils

import com.example.firebaseapp.model.Author

object AuthorMapper {

    fun getAuthorNames(authors: List<Author>): List<String> {
        if (authors.isNotEmpty()) {
            return authors.map { author ->
                reverseAuthorName(author.name)
            }
        }

        return listOf("Author Unknown")
    }

    private fun reverseAuthorName(author: String): String {
        if (author.split(",").size > 1) {
            return "${author.split(",")[1].trim()} " +
                    author.split(",")[0].trim()
        }
         return author
    }
}