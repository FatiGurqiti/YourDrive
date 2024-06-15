package com.fdev.yourdrive.domain.usecase.backupService

import com.fdev.yourdrive.domain.service.BackupServiceHelper

class StopBackupServiceUseCase(private val backupServiceHelper: BackupServiceHelper) {
    operator fun invoke(){
        backupServiceHelper.stop()
    }
}