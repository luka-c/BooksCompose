package com.example.firebaseapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    label: String,
    singleLine: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    isEnabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    error: String? = null,
    trailingIcon: @Composable () -> Unit = {},
    onAction: () -> Unit = {}
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text(text = label) },
        shape = shape,
        singleLine = singleLine,
        enabled = isEnabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
            onSearch = {
                keyboardController?.hide()
                focusManager.clearFocus()
                onAction()
            }
        ),
        visualTransformation = visualTransformation,
        isError = error != null,
        trailingIcon = trailingIcon
    )
}