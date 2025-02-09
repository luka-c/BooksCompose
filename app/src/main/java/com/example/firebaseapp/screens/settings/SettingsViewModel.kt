package com.example.firebaseapp.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseapp.di.AppDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: AppDataStore
) : ViewModel() {

    private val _theme = MutableStateFlow("system")
    val theme = _theme.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.getTheme().collect {
                _theme.value = it
            }
        }
    }

    fun saveTheme(theme: String) = viewModelScope.launch {
        dataStore.setTheme(theme)
    }
}