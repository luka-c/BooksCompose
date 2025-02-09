package com.example.firebaseapp.components

import androidx.compose.runtime.Composable

@Composable
fun RemoteDataView(
    error: String?,
    isLoading: Boolean,
    errorAction: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    if (isLoading) {
        Loading()
    }
    else if (error != null) {
        ErrorView(error = error, onAction = errorAction)
    }
    else {
        content()
    }

}