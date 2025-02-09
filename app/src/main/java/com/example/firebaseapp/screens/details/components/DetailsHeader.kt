package com.example.firebaseapp.screens.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.firebaseapp.model.Book
import com.example.firebaseapp.utils.AuthorMapper

@Composable
fun DetailsHeader(book: Book) {
    val authors = AuthorMapper.getAuthorNames(book.authors)
    val image = book.formats["image/jpeg"]

    Column(modifier = Modifier.padding(top = 12.dp)) {

        Text(
            text = book.title,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(modifier = Modifier.fillMaxWidth())
        {
            AsyncImage(
                model = image,
                contentDescription = "${book.title} cover",
                modifier = Modifier
                    .width(120.dp)
                    .height(180.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem(label = "Main Author", value = authors.first())
                InfoItem(label = "Languages", value = book.languages.joinToString(",").uppercase())
                InfoItem(label = "Download Count", value = book.download_count.toString())
            }
        }
    }
}