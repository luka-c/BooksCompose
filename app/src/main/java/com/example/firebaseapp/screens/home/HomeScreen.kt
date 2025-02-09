package com.example.firebaseapp.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.firebaseapp.components.AppBar
import com.example.firebaseapp.components.BottomBar
import com.example.firebaseapp.components.RemoteDataView
import com.example.firebaseapp.navigation.Routes
import com.example.firebaseapp.screens.home.components.BookCard

@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel) {
    Scaffold(
        topBar = { AppBar(navController) },
        bottomBar = { BottomBar(navController) }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            HomeScreenContent(navController, homeViewModel)
        }
    }
}

@Composable
fun HomeScreenContent(navController: NavHostController, homeViewModel: HomeViewModel) {
    RemoteDataView(
        error = homeViewModel.error.value, 
        isLoading = homeViewModel.isLoading.value,
        errorAction = { homeViewModel.getBooks() }
    ) {
        val books = homeViewModel.books.collectAsStateWithLifecycle().value

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            item {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Discover books",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
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
                    if (!homeViewModel.isLoading.value) {
                        homeViewModel.loadMoreBooks()
                    }
                }
            }
        }
    }
}
