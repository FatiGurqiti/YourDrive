package com.fdev.yourdrive.domain.usecase.backupStatus

import com.fdev.yourdrive.domain.repository.BackupStatusRepository

class GetBackupStatusUseCase(private val backupStatusRepository: BackupStatusRepository) {
    suspend operator fun invoke() = backupStatusRepository.getBackupStatus()
}