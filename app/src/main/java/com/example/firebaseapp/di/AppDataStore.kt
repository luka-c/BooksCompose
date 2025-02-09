package com.example.firebaseapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.firebaseapp.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class AppDataStore @Inject constructor(
    @Named(Constants.THEME_DATA_STORE) private val dataStore: DataStore<Preferences>
) {

    companion object {
        private const val THEME = "theme"
        val theme = stringPreferencesKey(THEME)
    }

    fun getTheme(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[theme] ?: "system"
        }
    }

    suspend fun setTheme(value: String) {
        dataStore.edit { preferences ->
            preferences[theme] = value
        }
    }
}