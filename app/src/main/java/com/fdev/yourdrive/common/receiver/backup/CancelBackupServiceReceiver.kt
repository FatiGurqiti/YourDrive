package com.fdev.yourdrive.common.receiver.backup

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fdev.yourdrive.common.Constant
import com.fdev.yourdrive.domain.service.BackupServiceHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CancelBackupServiceReceiver : BroadcastReceiver() {

    @Inject
    lateinit var backupServiceHelper: BackupServiceHelper

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Constant.Service.Backup.ACTION_STOP) {
            backupServiceHelper.stop()
        }
    }
}