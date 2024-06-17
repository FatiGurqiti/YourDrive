package com.fdev.yourdrive.domain.repository

interface BackupStatusRepository {
    suspend fun getBackupStatus(): Boolean
    suspend fun setBackupStatus(status: Boolean)
}