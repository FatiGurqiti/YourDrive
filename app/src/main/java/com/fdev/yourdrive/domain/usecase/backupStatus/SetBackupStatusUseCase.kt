package com.fdev.yourdrive.domain.usecase.backupStatus

import com.fdev.yourdrive.domain.repository.BackupStatusRepository

class SetBackupStatusUseCase(private val backupStatusRepository: BackupStatusRepository) {
    suspend operator fun invoke(status: Boolean) = backupStatusRepository.setBackupStatus(status)
}