package com.fdev.yourdrive.data.local.dataStore

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.fdev.yourdrive.domain.dataStore.PreferenceDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PreferenceDataStoreImpl(
    private val context: Context,
    dataStoreName: String
) : PreferenceDataStore {

    private val Context.dataStore by preferencesDataStore(
        name = dataStoreName,
        produceMigrations = { context ->
            listOf(SharedPreferencesMigration(context, dataStoreName))
        }
    )

    override suspend fun <T> set(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    override suspend fun <T> get(key: Preferences.Key<T>, defaultValue: T): T {
        val preferences = context.dataStore.data.first()
        return preferences[key] ?: defaultValue
    }

    override fun <T> getFlow(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        val preferences = context.dataStore.data
        return preferences.map { it[key] ?: defaultValue }
    }

    override suspend fun <T> contains(key: Preferences.Key<T>): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[key] != null
    }

    override suspend fun <T> remove(key: Preferences.Key<T>) {
        context.dataStore.edit {
            it.remove(key)
        }
    }

    override suspend fun <T> remove(keys: List<Preferences.Key<T>>) {
        context.dataStore.edit {
            keys.forEach { key ->
                it.remove(key)
            }
        }
    }
}