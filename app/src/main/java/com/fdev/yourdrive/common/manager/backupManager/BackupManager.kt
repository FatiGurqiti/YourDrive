package com.fdev.yourdrive.common.manager.backupManager

import com.fdev.yourdrive.domain.model.NetworkAuth

interface BackupManager {
    suspend fun backup(networkAuth: NetworkAuth)
}