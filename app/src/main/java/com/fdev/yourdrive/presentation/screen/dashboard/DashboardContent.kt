package com.fdev.yourdrive.presentation.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fdev.yourdrive.R
import com.fdev.yourdrive.common.util.toProgressStyle

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
            text = stringResource(id = if (backupCompleted) R.string.backup_completed else R.string.thats_it),
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )

        if (showProgressBar) {

            Spacer(Modifier.fillMaxHeight(.1f))

            Text(
                text = stringResource(
                    R.string.backup_in_progress,
                    progress.toProgressStyle()
                ),
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.fillMaxHeight(.05f))
            
            LinearProgressIndicator(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth(.7f)
                ,
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