package com.fdev.yourdrive.common.service.backup

import android.app.NotificationManager
import android.app.PendingIntent
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
import com.fdev.yourdrive.common.receiver.backup.CancelBackupServiceReceiver
import com.fdev.yourdrive.common.util.FlowUtil
import com.fdev.yourdrive.common.util.toProgressStyle
import com.fdev.yourdrive.domain.usecase.backupManager.BackupUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BackupService: Service() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Inject
    lateinit var backupUseCase: BackupUseCase

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
            .addCancel(this)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        backup(notification, notificationManager)

        startForeground(1, notification.build())
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        serviceScope.cancel()
        stopSelf()
    }

    private fun backup(
        notification: NotificationCompat.Builder,
        notificationManager: NotificationManager
    ) {
        serviceScope.launch {
            FlowUtil<Float>().onErrorEmptyOrCompletion(
                request = backupUseCase(),
                action = { stop() },
                onEmptyMessage = getString(R.string.synced_up),
                onCompletedMessage = getString(R.string.backup_completed)
            ).collect {
                updateBackupStatus(notification, it, notificationManager)
            }
        }
    }

    private fun updateBackupStatus(
        notification: NotificationCompat.Builder,
        progress: Float,
        notificationManager: NotificationManager
    ) {

        val updatedNotification =
            notification.setContentText(
                getString(
                    R.string.backup_in_progress_text,
                    progress.toProgressStyle()
                )
            ).setProgress(100, progress.toInt(), false)

        notificationManager.notify(NOTIFICATION_ID, updatedNotification.build())
    }

    private fun NotificationCompat.Builder.addCancel(context: Context): NotificationCompat.Builder {
        context.apply {
            val cancelIntent = Intent(this, CancelBackupServiceReceiver::class.java)
            cancelIntent.action = ACTION_STOP

            val cancelPendingIntent = PendingIntent.getBroadcast(
                this, NOTIFICATION_ID,
                cancelIntent, PendingIntent.FLAG_IMMUTABLE
            )

            addAction(
                NotificationCompat.Action(
                    androidx.core.R.drawable.ic_call_answer,
                    getString(R.string.stop),
                    cancelPendingIntent,
                )
            )

            return this@addCancel
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
    }
}