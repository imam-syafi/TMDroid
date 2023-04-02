package com.edts.tmdroid.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionManager @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    val current: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[LOGGED_IN_USER_KEY]
        }

    suspend fun save(name: String) {
        dataStore.edit { preferences ->
            preferences[LOGGED_IN_USER_KEY] = name
        }
    }

    suspend fun remove() {
        dataStore.edit { preferences ->
            preferences.remove(LOGGED_IN_USER_KEY)
        }
    }

    private companion object {
        val LOGGED_IN_USER_KEY = stringPreferencesKey("LOGGED_IN_USER_KEY")
    }
}
