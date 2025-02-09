package com.example.firebaseapp.screens.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    val email = mutableStateOf(auth.currentUser?.email ?: "-")
    val displayName = mutableStateOf(auth.currentUser?.displayName ?: "-")

    fun logout(onLogOut: () -> Unit = {}) {
        auth.signOut()
        onLogOut()
    }

    fun updateDisplayName(
        newDisplayName: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (auth.currentUser != null) {

            val changeRequest = UserProfileChangeRequest.Builder()
                .setDisplayName(newDisplayName)
                .build()

            auth.currentUser!!.updateProfile(changeRequest)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        displayName.value = newDisplayName
                        onSuccess()
                    }

                    else {
                        onError(task.exception?.message ?: "Could not update display name")
                    }
                }
        }
    }
}