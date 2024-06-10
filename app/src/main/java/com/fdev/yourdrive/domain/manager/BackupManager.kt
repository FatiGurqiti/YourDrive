package com.fdev.yourdrive.domain.manager

import com.fdev.yourdrive.domain.enumeration.Result
import com.fdev.yourdrive.domain.model.NetworkAuth

interface BackupManager {
    suspend fun connect(networkAuth: NetworkAuth): Result
    suspend fun backup()
}