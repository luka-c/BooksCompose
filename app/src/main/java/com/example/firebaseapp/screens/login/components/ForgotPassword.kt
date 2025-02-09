package com.example.firebaseapp.screens.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.firebaseapp.components.MyTextField
import com.example.firebaseapp.components.ShowAlertDialog
import com.example.firebaseapp.screens.login.showError
import com.example.firebaseapp.utils.FormHelpers

@Composable
fun ForgotPassword(onSend: (String) -> Unit = {}) {
    val context = LocalContext.current

    val showDialog = remember {
        mutableStateOf(false)
    }

    val email = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf<String?>(null) }

    Text(
        text = "Forgot Password?",
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp)
            .clickable {
                showDialog.value = true
            },
        textAlign = TextAlign.End,
        style = MaterialTheme.typography.labelLarge,
    )

    if (showDialog.value)
        ShowAlertDialog(
            onConfirmation = {
                emailError.value = FormHelpers.validateEmail(email.value)

                if (emailError.value != null) {
                    showError(error = emailError.value!!, context)
                }
                else {
                    onSend(email.value)
                    showDialog.value = false
                }
            },
            confirmationText = "Send",
            onDismissRequest = {
                showDialog.value = false
            },
            title = "Request Password Change",
            content = {
                MyTextField(
                    text = email,
                    label = "Email",
                    imeAction = ImeAction.Done,
                    error = emailError.value,
                    keyboardType = KeyboardType.Email
                )
            },
            icon = Icons.Filled.Refresh
        )
}