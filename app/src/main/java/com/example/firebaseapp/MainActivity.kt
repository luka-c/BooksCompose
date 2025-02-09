package com.example.firebaseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.firebaseapp.di.AppDataStore
import com.example.firebaseapp.navigation.AppNavigation
import com.example.firebaseapp.navigation.Routes
import com.example.firebaseapp.ui.theme.FirebaseAppTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStore: AppDataStore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        // Check if user is logged in
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val startingRoute = if (currentUser != null) Routes.MAIN else Routes.LOGIN

        setContent {
            MyApp(startingRoute, dataStore)
        }
    }
}

@Composable
fun MyApp(startingRoute: Routes, dataStore: AppDataStore) {
    val theme = dataStore.getTheme().collectAsStateWithLifecycle(initialValue = "system").value

    val isDarkMode = when (theme) {
        "dark" -> true
        "light" -> false
        "system" -> isSystemInDarkTheme()
        else -> isSystemInDarkTheme()
    }

    FirebaseAppTheme(darkTheme = isDarkMode) {
        AppNavigation(startingRoute)
    }
}