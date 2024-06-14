package com.fdev.yourdrive.data.service

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.fdev.yourdrive.R
import com.fdev.yourdrive.common.Constant.Service.Backup.ACTION_START
import com.fdev.yourdrive.common.Constant.Service.Backup.ACTION_STOP
import com.fdev.yourdrive.common.Constant.Service.Backup.CHANNEL_ID
import com.fdev.yourdrive.common.Constant.Service.Backup.NOTIFICATION_ID
import com.fdev.yourdrive.common.util.FlowUtil
import com.fdev.yourdrive.common.util.toProgressStyle
import com.fdev.yourdrive.domain.manager.BackupManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BackupService : Service() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Inject
    lateinit var backupManager: BackupManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.backup_in_progress_title))
            .setContentText(getString(R.string.backup_preparing))
            .setProgress(100, 0, true)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val flowUtil = FlowUtil<Float>()

        serviceScope.launch {
            flowUtil.onErrorEmptyOrCompletion(
                request = backupManager.backup(),
                action = {
                    serviceScope.cancel(it)
                    stopSelf()
                },
                onEmptyMessage = getString(R.string.synced_up),
                onCompletedMessage = getString(R.string.backup_completed)
            ).collect {
                val updatedNotification =
                    notification.setContentText(
                        getString(
                            R.string.backup_in_progress_text,
                            it.toProgressStyle()
                        )
                    ).setProgress(100, it.toInt(), false)

                notificationManager.notify(NOTIFICATION_ID, updatedNotification.build())
            }
        }

        startForeground(1, notification.build())
    }

    private fun stop() {
        stopForeground(true)
        serviceScope.cancel()
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}