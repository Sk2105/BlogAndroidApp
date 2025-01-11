package com.composeapp.blogapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TokenRepository(
    private val storage: DataStore<Preferences>
) {

    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
    }

    val accessTokenFlow: Flow<String> = storage.data.map { preferences ->
        preferences[ACCESS_TOKEN] ?: ""
    }.flowOn(Dispatchers.IO)

    suspend fun saveAccessToken(accessToken: String) {
        storage.edit { it[ACCESS_TOKEN] = accessToken }
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "token"
)