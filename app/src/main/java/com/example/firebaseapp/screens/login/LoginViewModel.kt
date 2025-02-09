package com.example.firebaseapp.screens.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    val isLoading = mutableStateOf(false)

    fun login(
        email: String,
        password: String,
        successCallback: () -> Unit,
        errorCallback: (String) -> Unit,
    ) {
        isLoading.value = true

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            isLoading.value = false

            if (task.isSuccessful) {
                successCallback()
            }
            else {
                errorCallback(task.exception?.message ?: "Authentication failed")
            }
        }
    }

    fun requestChangePassword(
        email: String,
        successCallback: (String) -> Unit,
        errorCallback: (String) -> Unit,
    ) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                successCallback("Password change requested")
            }
            else {
                errorCallback(task.exception?.message ?: "Request failed")
            }
        }
    }
}