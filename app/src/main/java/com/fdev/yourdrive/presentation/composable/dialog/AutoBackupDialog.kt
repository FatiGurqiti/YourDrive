package com.fdev.yourdrive.presentation.composable.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.fdev.yourdrive.R

@Composable
fun AutoBackupDialog(
    onConfirm: () -> Unit,
    onDecline: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDecline,
        title = {
            Text(
                text = stringResource(R.string.auto_backup_dialog_title),
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp
            )
        }, text = {
            Text(text = stringResource(R.string.auto_backup_dialog_message))
        }, confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(
                    text = stringResource(R.string.allow),
                )
            }
        }, dismissButton = {
            Button(
                onClick = onDecline
            ) {
                Text(
                    text = stringResource(R.string.decline),
                )
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun AutoBackupDialogPreview() {
    AutoBackupDialog({}, {})
}