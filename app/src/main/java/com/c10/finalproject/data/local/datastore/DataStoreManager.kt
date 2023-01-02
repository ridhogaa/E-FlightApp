package com.c10.finalproject.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class DataStoreManager(@ApplicationContext private val context: Context) {

    val getToken: Flow<String> = context.dataStore.data.map {
        it[TOKEN_USER_KEY] ?: ""
    }

    suspend fun setToken(token: String) {
        context.dataStore.edit {
            it[TOKEN_USER_KEY] = "Bearer $token"
        }
    }

    val getId: Flow<Int> = context.dataStore.data.map {
        it[ID_USER_KEY] ?: 0
    }

    suspend fun setId(id: Int) {
        context.dataStore.edit {
            it[ID_USER_KEY] = id
        }
    }

    suspend fun clear() {
        context.dataStore.edit {
            it.clear()
        }
    }

    companion object {
        private const val DATASTORE_NAME = "datastore_preferences"
        private val TOKEN_USER_KEY = stringPreferencesKey("token_user_key")
        private val ID_USER_KEY = intPreferencesKey("id_user_key")
        private val Context.dataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}