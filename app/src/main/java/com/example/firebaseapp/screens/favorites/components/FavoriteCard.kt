package com.example.firebaseapp.screens.favorites.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.firebaseapp.data.database_model.FavoriteBook

@Composable
fun FavoriteCard(
    favoriteBook: FavoriteBook,
    onClick: (FavoriteBook) -> Unit = {},
    onDelete: (FavoriteBook) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(bottom = 10.dp),
        onClick = { onClick(favoriteBook) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(12.dp),
        ) {
            AsyncImage(
                model = favoriteBook.imageUrl,
                contentDescription = "${favoriteBook.title} cover",
                modifier = Modifier
                    .height(80.dp)
                    .width(60.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(text = favoriteBook.title, modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxHeight().weight(0.35f).padding(end = 4.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Favorite",
                    modifier = Modifier.clickable {
                        onDelete(favoriteBook)
                    }
                )
            }
        }
    }
}