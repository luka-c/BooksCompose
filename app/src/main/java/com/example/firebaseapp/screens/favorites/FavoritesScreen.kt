package com.example.firebaseapp.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.firebaseapp.R
import com.example.firebaseapp.components.AppBar
import com.example.firebaseapp.components.BottomBar
import com.example.firebaseapp.components.ShowAlertDialog
import com.example.firebaseapp.navigation.Routes
import com.example.firebaseapp.screens.favorites.components.FavoriteCard

@Composable
fun FavoritesScreen(navController: NavHostController, favoritesViewModel: FavoritesViewModel) {
    Scaffold(
        topBar = { AppBar(navController) },
        bottomBar = { BottomBar(navController) }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            FavoritesScreenContent(navController, favoritesViewModel)
        }
    }
}

@Composable
fun FavoritesScreenContent(navController: NavHostController, favoritesViewModel: FavoritesViewModel) {
    val favoriteBooks = favoritesViewModel.favoriteBooks.collectAsStateWithLifecycle().value
    
    if (favoriteBooks.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.no_favorites),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(
                text = stringResource(R.string.favorites_tip),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 36.dp)
            )
        }
    }
    else {
        val showDeleteDialog = remember { mutableStateOf(false) }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Favorites",
                        style = MaterialTheme.typography.headlineSmall,
                    )

                    Button(
                        onClick = {
                            showDeleteDialog.value = true
                        }
                    ) {
                        Text(text = "Delete All")
                    }

                    if (showDeleteDialog.value)
                        ShowAlertDialog(
                            onConfirmation = {
                                favoritesViewModel.deleteAllFavorites()
                            },
                            confirmationText = "Delete All",
                            onDismissRequest = {
                                showDeleteDialog.value = false
                            },
                            title = "Confirm Delete",
                            content = {
                                Text(text = stringResource(R.string.delete_favorites_text))
                            },
                            icon = Icons.Filled.Warning
                        )
                }
            }

            items(favoriteBooks) { favoriteBook ->
                FavoriteCard(
                    favoriteBook = favoriteBook,
                    onClick = {
                        navController.navigate("${Routes.DETAILS.name}/${it.id}" )
                    },
                    onDelete = { favoritesViewModel.deleteFavorite(it) }
                )
            }
        }
    }
}