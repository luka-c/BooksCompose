package com.example.firebaseapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.firebaseapp.navigation.Routes

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            selected = navController.currentDestination?.route == Routes.HOME.name,
            icon = { Icon(imageVector = Icons.Rounded.Home, contentDescription = "Home Icon")},
            label = { Text(text = "Home") },
            onClick = { navController.navigate(route = Routes.HOME.name) },
        )

        NavigationBarItem(
            selected = navController.currentDestination?.route == Routes.FAVORITES.name,
            icon = { Icon(imageVector = Icons.Rounded.Favorite, contentDescription = "Favorites Icon")},
            label = { Text(text = "Favorites") },
            onClick = { navController.navigate(route = Routes.FAVORITES.name) },
        )

        NavigationBarItem(
            selected = navController.currentDestination?.route == Routes.SEARCH.name,
            icon = { Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search Icon")},
            label = { Text(text = "Search") },
            onClick = { navController.navigate(route = Routes.SEARCH.name) },
        )

        NavigationBarItem(
            selected = navController.currentDestination?.route == Routes.SETTINGS.name,
            icon = { Icon(imageVector = Icons.Rounded.Settings, contentDescription = "Settings Icon")},
            label = { Text(text = "Settings") },
            onClick = { navController.navigate(route = Routes.SETTINGS.name) },
        )
    }
}