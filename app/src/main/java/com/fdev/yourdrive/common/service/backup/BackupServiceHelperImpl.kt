package com.fdev.yourdrive.common.service.backup

import android.content.Context
import android.content.Intent
import com.fdev.yourdrive.common.Constant
import com.fdev.yourdrive.domain.service.BackupServiceHelper

class BackupServiceHelperImpl(private val context: Context) : BackupServiceHelper {

    override fun start() {
        context.apply {
            Intent(this, BackupService::class.java).apply {
                action = Constant.Service.Backup.ACTION_START
                startService(this)
            }
        }
    }

    override fun stop() {
        context.apply {
            Intent(this, BackupService::class.java).apply {
                action = Constant.Service.Backup.ACTION_STOP
                startService(this)
            }
        }
    }
}