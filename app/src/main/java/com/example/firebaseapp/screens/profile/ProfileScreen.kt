package com.example.firebaseapp.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.firebaseapp.components.AppBar
import com.example.firebaseapp.components.MyTextField
import com.example.firebaseapp.navigation.Routes
import com.example.firebaseapp.screens.login.showError

@Composable
fun ProfileScreen(navController: NavHostController, profileViewModel: ProfileViewModel) {
    Scaffold(
        topBar = { AppBar(navController, "Profile", true) },
    ) {
        Surface(modifier = Modifier.padding(it)) {
            ProfileScreenContent(navController, profileViewModel)
        }
    }
}

@Composable
fun ProfileScreenContent(navController: NavHostController, profileViewModel: ProfileViewModel) {
    val context = LocalContext.current

    val editMode = remember {
        mutableStateOf(false)
    }

    val displayName = remember(profileViewModel.displayName) {
        mutableStateOf(profileViewModel.displayName.value)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "EMAIL",
                style = MaterialTheme.typography.labelLarge
            )
            MyTextField(
                text = profileViewModel.email,
                label = "",
                isEnabled = false
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "DISPLAY NAME",
                style = MaterialTheme.typography.labelLarge
            )

            MyTextField(
                text = displayName,
                label = "",
                isEnabled = editMode.value,
                imeAction = ImeAction.Done,
                trailingIcon = {
                    if (!editMode.value)
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit Icon",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    editMode.value = true
                                }
                        )
                }
            )

            if (editMode.value) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            displayName.value = profileViewModel.displayName.value
                            editMode.value = false
                        }
                    ) {
                        Text(text = "Cancel")
                    }
                    
                    Button(
                        onClick = {
                            profileViewModel.updateDisplayName(
                                newDisplayName = displayName.value,
                                onSuccess = {
                                    editMode.value = false
                                },
                                onError = {
                                    showError(error = it, context = context)
                                }
                            )
                        }
                    ) {
                        Text(text = "Change Display Name")
                    }
                }
            }
        }

        Button(
            onClick = {
                profileViewModel.logout(
                    onLogOut = {
                        navController.navigate(Routes.LOGIN.name) {
                            popUpTo(Routes.MAIN.name)
                        }
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "LOG OUT")
        }
    }
}