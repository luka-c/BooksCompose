package com.example.firebaseapp.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebaseapp.components.AppBar
import com.example.firebaseapp.components.MyTextField
import com.example.firebaseapp.navigation.Routes
import com.example.firebaseapp.screens.login.showError
import com.example.firebaseapp.utils.FormHelpers

@Composable
fun RegisterScreen(navController: NavController, registerViewModel: RegisterViewModel) {
    Scaffold(
        topBar = { AppBar(navController, "Register", true) }
    ) { innerPadding ->
        Surface(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            RegisterContent(navController, registerViewModel)
        }
    }
}

@Composable
fun RegisterContent(navController: NavController, registerViewModel: RegisterViewModel) {
    val context = LocalContext.current

    val email = rememberSaveable { mutableStateOf("") }
    val emailError = rememberSaveable { mutableStateOf<String?>(null) }

    val displayName = rememberSaveable { mutableStateOf("") }
    val displayNameError = rememberSaveable { mutableStateOf<String?>(null) }

    val password = rememberSaveable { mutableStateOf("") }
    val passwordError = rememberSaveable { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        MyTextField(
            text = email,
            label = "Email",
            error = emailError.value,
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(6.dp))

        MyTextField(
            text = displayName,
            label = "Display Name",
            error = displayNameError.value,
        )

        Spacer(modifier = Modifier.height(6.dp))

        MyTextField(
            text = password,
            label = "Password",
            error = passwordError.value,
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                emailError.value = FormHelpers.validateEmail(email.value)
                displayNameError.value = FormHelpers.validateDisplayName(displayName.value)
                passwordError.value = FormHelpers.validatePassword(password.value)

                if (emailError.value != null) {
                    showError(error = emailError.value!!, context)
                }
                else if (displayNameError.value != null) {
                    showError(error = displayNameError.value!!, context)
                }
                else if (passwordError.value != null) {
                    showError(error = passwordError.value!!, context)
                }
                else {
                    registerViewModel.register(
                        email.value,
                        password.value,
                        displayName.value,
                        successCallback = {
                            navController.navigate(route = Routes.MAIN.name) {
                                popUpTo(Routes.LOGIN.name) { inclusive = true }
                            }
                        },
                        errorCallback = { showError(it, context) }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 15.dp),
            enabled = !registerViewModel.isLoading.value
        ) {
            if (registerViewModel.isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.size(17.dp))
            }
            else {
                Text(text = "Register", fontWeight = FontWeight.Bold, fontSize = 17.sp)
            }
        }
    }
}
