package com.fdev.yourdrive.domain.dataStore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferenceDataStore {

    suspend fun <T> set(
        key: Preferences.Key<T>,
        value: T
    )

    suspend fun <T> get(
        key: Preferences.Key<T>,
        defaultValue: T
    ) : T

    fun <T> getFlow(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T>

    suspend fun <T> contains(key: Preferences.Key<T>) : Boolean

    suspend fun <T> remove(key: Preferences.Key<T>)

    suspend fun <T> remove(keys: List<Preferences.Key<T>>)
}