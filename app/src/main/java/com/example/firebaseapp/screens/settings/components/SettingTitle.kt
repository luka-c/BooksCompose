package com.example.firebaseapp.screens.settings.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title.uppercase(),
        modifier = modifier.padding(start = 3.dp, bottom = 6.dp),
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold
    )
}