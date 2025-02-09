package com.example.firebaseapp.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.firebaseapp.components.AppBar
import com.example.firebaseapp.components.BottomBar
import com.example.firebaseapp.components.RemoteDataView
import com.example.firebaseapp.screens.details.components.DetailsHeader
import com.example.firebaseapp.screens.details.components.InfoItem

@Composable
fun DetailsScreen(navController: NavController, detailsViewModel: DetailsViewModel) {
    Scaffold(
        topBar = { 
            AppBar(
                navController = navController, 
                title = "",
                hasNavigationIcon = true,
                actions = {
                    IconButton(
                        onClick = {
                            if (detailsViewModel.isFavorite.value)
                                detailsViewModel.removeFromFavorites()
                            else
                                detailsViewModel.addToFavorites()
                        }
                    ) {
                        if (detailsViewModel.isFavorite.value)
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Unfavorite Icon"
                            )
                        else
                            Icon(
                                imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription = "Favorite Icon"
                            )
                    }
                }
            ) 
        },
    ) {
        Surface(modifier = Modifier.padding(it)) {
            DetailsContent(detailsViewModel)
        }
    }
}

@Composable
fun DetailsContent(detailsViewModel: DetailsViewModel) {
    RemoteDataView(
        error = detailsViewModel.error.value,
        isLoading = detailsViewModel.isLoading.value,
        errorAction = { detailsViewModel.getBookDetails() }
    ) {
        val book = detailsViewModel.book.collectAsStateWithLifecycle().value

        if (book != null) {
            LazyColumn(modifier = Modifier.padding(horizontal = 12.dp)) {
                item {
                    DetailsHeader(book)
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(text = "Subjects", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }

                items(book.subjects) { subject ->
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                    Text(text = subject, modifier = Modifier.padding(horizontal = 12.dp))
                }

                item {
                    HorizontalDivider(modifier = Modifier.padding(top = 10.dp, bottom = 40.dp))
                    Text(text = "Bookshelves", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }

                items(book.bookshelves) { bookshelf ->
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                    Text(text = bookshelf, modifier = Modifier.padding(horizontal = 12.dp))
                }
                
                item {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                }
            }
        }

    }
}
