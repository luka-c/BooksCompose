package com.example.firebaseapp.utils

object FormHelpers {

    fun validateEmail(input: String): String? {
        return if (input.isEmpty()) {
            "Email cannot be empty"
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            "Invalid email address"
        } else {
            null
        }
    }

    fun validateDisplayName(input: String): String? {
        return if (input.isEmpty()) {
            "Display name cannot be empty"
        } else {
            null
        }
    }

    fun validatePassword(input: String): String? {
        return when {
            input.isEmpty() -> "Password cannot be empty"
            input.length < 8 -> "Password must be at least 8 characters long"
            else -> null
        }
    }
}