package com.fdev.yourdrive.data.local.repository

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.fdev.yourdrive.domain.dataStore.PreferenceDataStore
import com.fdev.yourdrive.domain.repository.AppStateRepository

class AppStateRepositoryImpl(private val dataStore: PreferenceDataStore): AppStateRepository {

    companion object {
        const val FIRST_LOAD = "firstLoad"
    }

    override suspend fun isFirstLoad(): Boolean =
        dataStore.get(booleanPreferencesKey(FIRST_LOAD), true)

    override suspend fun updateFirstLoad() {
        dataStore.set(booleanPreferencesKey(FIRST_LOAD), false)
    }
}