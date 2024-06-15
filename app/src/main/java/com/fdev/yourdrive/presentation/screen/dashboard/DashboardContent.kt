package com.fdev.yourdrive.presentation.screen.dashboard

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fdev.yourdrive.R
import com.fdev.yourdrive.common.Constant
import com.fdev.yourdrive.common.util.toProgressStyle
import com.fdev.yourdrive.common.service.BackupServiceImpl

@Composable
fun DashboardContent(
    backupCompleted: Boolean,
    showProgressBar: Boolean,
    progress: Float
) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = if (backupCompleted) R.string.synced_up else R.string.thats_it),
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )

        val context = LocalContext.current

        Button(onClick = { }) {
            Text(text = "Start backup on vm")
        }

        Button(onClick = {
            context.applicationContext.apply {
                Intent(this, BackupServiceImpl::class.java).apply {
                    action = Constant.Service.Backup.ACTION_START
                    startService(this)
                }
            }
        }) {
            Text(text = "Start backup service")
        }

        Button(onClick = {
            context.applicationContext.apply {
                Intent(this, BackupServiceImpl::class.java).apply {
                    action = Constant.Service.Backup.ACTION_STOP
                    startService(this)
                }
            }
        }) {
            Text(text = "Stop backup service")
        }

        if (showProgressBar) {

            Spacer(Modifier.fillMaxHeight(.1f))

            Text(
                text = stringResource(
                    R.string.backup_in_progress_text,
                    progress.toProgressStyle()
                ),
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.fillMaxHeight(.05f))

            LinearProgressIndicator(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth(.7f),
                progress = progress
            )
        }
    }
}


@Preview
@Composable
fun DashboardContentPreview() {
    DashboardContent(backupCompleted = false, showProgressBar = true, progress = 50f)
}