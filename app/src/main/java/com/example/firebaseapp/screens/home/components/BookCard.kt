package com.example.firebaseapp.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.firebaseapp.model.Book
import com.example.firebaseapp.utils.AuthorMapper

@Composable
fun BookCard(book: Book, onClick: () -> Unit = {}) {
    val firstTitle = book.title.split(";")[0]

    val title =
        if (firstTitle.length > 40)
            firstTitle.slice(IntRange(0, 40)).trim() + "..."
        else firstTitle

    val authors = AuthorMapper.getAuthorNames(book.authors)

    val image = book.formats["image/jpeg"]

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = "Book Cover",
                modifier = Modifier
                    .height(80.dp)
                    .width(60.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(5.dp))
                
                Text(
                    text = "Authors: ${authors.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Languages: ${book.languages.joinToString(",").uppercase()}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Downloads: ${book.download_count}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }
}