package com.fdev.yourdrive.domain.usecase.backup

import com.fdev.yourdrive.domain.manager.BackupManager

class BackupUseCase (private val backupManager: BackupManager) {
    suspend operator fun invoke() = backupManager.backup()
}