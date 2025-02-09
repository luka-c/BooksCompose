package com.example.firebaseapp.screens.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    val isLoading = mutableStateOf(false)

    fun register(
        email: String,
        password: String,
        displayName: String,
        successCallback: () -> Unit,
        errorCallback: (String) -> Unit,
    ) {
        isLoading.value = true

        val changeRequest = UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .build()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                task.result.user!!.updateProfile(changeRequest).addOnCompleteListener { updateTask ->

                    if (updateTask.isSuccessful) {
                        isLoading.value = false
                        successCallback()
                    }
                    else {
                        errorCallback(updateTask.exception?.message ?: "Adding display name failed")
                    }
                }
            }
            else {
                errorCallback(task.exception?.message ?: "Register failed")
            }
        }
    }
}