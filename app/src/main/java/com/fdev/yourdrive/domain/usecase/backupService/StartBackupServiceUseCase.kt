package com.fdev.yourdrive.domain.usecase.backupService

import com.fdev.yourdrive.domain.service.BackupServiceHelper

class StartBackupServiceUseCase(private val backupServiceHelper: BackupServiceHelper) {
    operator fun invoke(){
        backupServiceHelper.start()
    }
}