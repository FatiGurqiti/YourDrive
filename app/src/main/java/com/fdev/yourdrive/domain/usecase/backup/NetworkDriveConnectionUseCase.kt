package com.fdev.yourdrive.domain.usecase.backup

import com.fdev.yourdrive.domain.enumeration.Result
import com.fdev.yourdrive.domain.manager.BackupManager
import com.fdev.yourdrive.domain.model.NetworkAuth

class NetworkDriveConnectionUseCase(private val backupManager: BackupManager) {
    suspend operator fun invoke(networkAuth: NetworkAuth): Result =
        backupManager.connect(networkAuth)
}