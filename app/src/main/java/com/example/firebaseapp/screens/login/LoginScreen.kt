package com.example.firebaseapp.screens.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebaseapp.components.MyTextField
import com.example.firebaseapp.navigation.Routes
import com.example.firebaseapp.screens.login.components.ForgotPassword
import com.example.firebaseapp.screens.login.components.LoginHeader
import com.example.firebaseapp.utils.FormHelpers

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel) {
    LoginForm(navController, loginViewModel)
}

@Composable
fun LoginForm(navController: NavController, loginViewModel: LoginViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val context = LocalContext.current

        val email = rememberSaveable { mutableStateOf("") }
        val emailError = rememberSaveable { mutableStateOf<String?>(null) }

        val password = rememberSaveable { mutableStateOf("") }
        val passwordError = rememberSaveable { mutableStateOf<String?>(null) }

        LoginHeader()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, top = 150.dp),
        ) {
            MyTextField(
                text = email,
                label = "Email",
                error = emailError.value,
                keyboardType = KeyboardType.Email
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

            Spacer(modifier = Modifier.height(8.dp))

            ForgotPassword {
                loginViewModel.requestChangePassword(
                    email = it,
                    successCallback = { successMsg -> showError(successMsg, context) },
                    errorCallback = { errorMsg -> showError(errorMsg, context) }
                )
            }

            // LOGIN BUTTON
            Button(
                onClick = {
                    emailError.value = FormHelpers.validateEmail(email.value)
                    passwordError.value = FormHelpers.validatePassword(password.value)

                    if (emailError.value != null) {
                        showError(error = emailError.value!!, context)
                    }
                    else if (passwordError.value != null) {
                        showError(error = passwordError.value!!, context)
                    }
                    else {
                        loginViewModel.login(
                            email.value,
                            password.value,
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
                enabled = !loginViewModel.isLoading.value
            ) {
                if (loginViewModel.isLoading.value) {
                    CircularProgressIndicator(modifier = Modifier.size(17.dp))
                }
                else {
                    Text(text = "Login", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))


            // REGISTER BUTTON
            OutlinedButton(
                onClick = { navController.navigate(Routes.REGISTER.name) },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 15.dp)
            ) {
                Text(text = "Register", fontWeight = FontWeight.Bold, fontSize = 17.sp)
            }
        }
    }
}

fun showError(error: String, context: Context) {
    Toast.makeText(
        context,
        error,
        Toast.LENGTH_SHORT,
    ).show()
}
