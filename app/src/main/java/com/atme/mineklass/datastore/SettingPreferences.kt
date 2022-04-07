package com.atme.mineklass.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences(val context: Context) {


    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = "Setting_pref"
        )

        private val DARK_THEME_MODE = booleanPreferencesKey(name = "dark_theme_mode")
    }

    suspend fun changeDarkMode(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_THEME_MODE] = value
        }
    }

    val darkModeStatus: Flow<Boolean> =
        context.dataStore.data.map { pref -> pref[DARK_THEME_MODE] ?: false }

}

