package com.fdev.yourdrive.data.local.repository

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.fdev.yourdrive.domain.dataStore.PreferenceDataStore
import com.fdev.yourdrive.domain.repository.BackupStatusRepository

class BackupStatusRepositoryImpl(
    private val dataStore: PreferenceDataStore
) : BackupStatusRepository {

    companion object {
        private const val BACKUP_STATUS = "backupStatus"
    }

    override suspend fun getBackupStatus(): Boolean = dataStore.get(
        booleanPreferencesKey(
            BACKUP_STATUS
        ), false
    )

    override suspend fun setBackupStatus(status: Boolean) {
        dataStore.set(booleanPreferencesKey(BACKUP_STATUS), status)
    }
}