package com.example.firebaseapp.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.firebaseapp.R
import com.example.firebaseapp.components.AppBar
import com.example.firebaseapp.components.BottomBar
import com.example.firebaseapp.screens.settings.components.SettingTitle
import com.example.firebaseapp.screens.settings.components.SettingValue
import com.example.firebaseapp.screens.settings.components.ThemeSwitcher

@Composable
fun SettingsScreen(navController: NavHostController, settingsViewModel: SettingsViewModel) {
    Scaffold(
        topBar = { AppBar(navController) },
        bottomBar = { BottomBar(navController) }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            SettingsScreenContent(settingsViewModel)
        }
    }
}

@Composable
fun SettingsScreenContent(settingsViewModel: SettingsViewModel) {
    val theme = settingsViewModel.theme.collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(40.dp))

        SettingTitle(title = "Current Theme", modifier = Modifier.padding(bottom = 3.dp))
        ThemeSwitcher(
            theme = theme,
            onSelectTheme = {
                settingsViewModel.saveTheme(it)
            }
        )

        Spacer(modifier = Modifier.height(25.dp))

        SettingTitle(title = "App Version")
        SettingValue(value = "1.0.0")

        Spacer(modifier = Modifier.height(25.dp))

        SettingTitle(title = "About")
        SettingValue(value = stringResource(R.string.About))
    }
}