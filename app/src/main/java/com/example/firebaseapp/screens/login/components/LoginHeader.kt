package com.example.firebaseapp.screens.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginHeader() {
    Surface(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, top = 12.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Sign in to your account",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 40.sp
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Please sign in to your account or register",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}