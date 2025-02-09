package com.example.firebaseapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firebaseapp.screens.details.DetailsScreen
import com.example.firebaseapp.screens.details.DetailsViewModel
import com.example.firebaseapp.screens.favorites.FavoritesScreen
import com.example.firebaseapp.screens.favorites.FavoritesViewModel
import com.example.firebaseapp.screens.home.HomeScreen
import com.example.firebaseapp.screens.home.HomeViewModel
import com.example.firebaseapp.screens.login.LoginScreen
import com.example.firebaseapp.screens.login.LoginViewModel
import com.example.firebaseapp.screens.profile.ProfileScreen
import com.example.firebaseapp.screens.profile.ProfileViewModel
import com.example.firebaseapp.screens.register.RegisterScreen
import com.example.firebaseapp.screens.register.RegisterViewModel
import com.example.firebaseapp.screens.search.SearchScreen
import com.example.firebaseapp.screens.search.SearchViewModel
import com.example.firebaseapp.screens.settings.SettingsScreen
import com.example.firebaseapp.screens.settings.SettingsViewModel

@Composable
fun AppNavigation(startingRoute: Routes) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startingRoute.name,
        enterTransition = { fadeIn(tween(0)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it })  },
        popExitTransition = { fadeOut(tween(0)) }
    ) {
        composable(route = Routes.LOGIN.name) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(navController, loginViewModel)
        }

        composable(
            route = Routes.REGISTER.name,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(navController, registerViewModel)
        }

        navigation(route = Routes.MAIN.name, startDestination = Routes.HOME.name) {

            composable(route = Routes.HOME.name) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomeScreen(navController, homeViewModel)
            }

            composable(
                route = Routes.DETAILS.name + "/{bookId}",
                arguments = listOf(navArgument(name = "bookId") { type = NavType.IntType}),
                enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
            ) {
                val detailsViewModel: DetailsViewModel = hiltViewModel()
                DetailsScreen(navController, detailsViewModel)
            }

            composable(route = Routes.FAVORITES.name) {
                val favoritesViewModel: FavoritesViewModel = hiltViewModel()
                FavoritesScreen(navController, favoritesViewModel)
            }

            composable(route = Routes.SEARCH.name) {
                val searchViewModel: SearchViewModel = hiltViewModel()
                SearchScreen(navController, searchViewModel)
            }

            composable(route = Routes.SETTINGS.name) {
                val settingsViewModel: SettingsViewModel = hiltViewModel()
                SettingsScreen(navController, settingsViewModel)
            }

            composable(
                route = Routes.PROFILE.name,
                enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
            ) {
                val profileViewModel: ProfileViewModel = hiltViewModel()
                ProfileScreen(navController, profileViewModel)
            }
        }

    }
}