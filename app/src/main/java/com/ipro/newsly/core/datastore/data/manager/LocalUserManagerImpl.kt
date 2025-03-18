package com.ipro.newsly.core.datastore.data.manager

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.ipro.newsly.core.datastore.domain.manager.LocalUserManager
import com.ipro.newsly.core.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserManagerImpl (
    private val context:Context,
): LocalUserManager {
    override suspend fun saveAppEntry() {
        try {
            context.dataStore.edit { settings ->
                settings[PreferencesKeys.APP_ENTRY] = true
            }
        } catch (e: Exception) {
            Log.e("LocalUserManagerImpl", "Failed to save APP_ENTRY", e)
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.APP_ENTRY] ?: false
        }
    }

    /*
    override suspend fun readAppEntry(): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.APP_ENTRY] ?: false
        }.first()
    }*/


}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.USER_SETTINGS)

private object PreferencesKeys {
    val APP_ENTRY = booleanPreferencesKey(Constants.APP_ENTRY)
}