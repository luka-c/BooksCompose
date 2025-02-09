package com.example.firebaseapp.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ShowAlertDialog(
    onConfirmation: () -> Unit,
    confirmationText: String,
    onDismissRequest: () -> Unit,
    dismissText: String = "Cancel",
    title: String,
    content: @Composable () -> Unit = {},
    icon: ImageVector,
) {
    AlertDialog(
        title = {
            Text(text = title)
        },
        text = content,
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text(text = confirmationText)
            }
        },
        onDismissRequest = onDismissRequest,
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = dismissText)
            }
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = "Dialog Icon"
            )
        }
    )
}