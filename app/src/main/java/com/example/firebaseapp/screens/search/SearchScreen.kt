package com.example.firebaseapp.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.firebaseapp.components.AppBar
import com.example.firebaseapp.components.BottomBar
import com.example.firebaseapp.components.ErrorView
import com.example.firebaseapp.components.Loading
import com.example.firebaseapp.components.MyTextField
import com.example.firebaseapp.navigation.Routes
import com.example.firebaseapp.screens.home.components.BookCard
import com.example.firebaseapp.screens.search.components.FillScreenContainer
import com.example.firebaseapp.screens.search.components.SearchPlaceholder

@Composable
fun SearchScreen(navController: NavHostController, searchViewModel: SearchViewModel) {
    Scaffold(
        topBar = { AppBar(navController) },
        bottomBar = { BottomBar(navController) }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            SearchScreenContent(navController, searchViewModel)
        }
    }
}

@Composable
fun SearchScreenContent(navController: NavHostController, searchViewModel: SearchViewModel) {

    val showPlaceholder = rememberSaveable {
        mutableStateOf(true)
    }

    val query = rememberSaveable {
        mutableStateOf("")
    }

    val books = searchViewModel.books.collectAsStateWithLifecycle().value

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 12.dp)
    ) {
        item {
            MyTextField(
                text = query,
                label = "",
                imeAction = ImeAction.Search,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon"
                    )
                },
                onAction = {
                    showPlaceholder.value = false
                    searchViewModel.search(query.value)
                },
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        if (showPlaceholder.value) {
            item {
                FillScreenContainer {
                    SearchPlaceholder()
                }
            }
        }
        else if (searchViewModel.error.value != null) {
            item {
                FillScreenContainer {
                    ErrorView(error = searchViewModel.error.value!!)
                }
            }
        }
        else if (searchViewModel.isLoading.value) {
            item {
                FillScreenContainer {
                    Loading()
                }
            }
        }
        else {
            if (books.isEmpty()) {
                item {
                    FillScreenContainer {
                        Text(
                            text = "No books found",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
            else {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }

                items(books) { book ->
                    BookCard(book = book) {
                        navController.navigate("${Routes.DETAILS.name}/${book.id}" )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(35.dp))
                    }
                }

                item {
                    LaunchedEffect(true) {
                        searchViewModel.loadMoreBooks()
                    }
                }
            }
        }
    }
}