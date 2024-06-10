package com.fdev.yourdrive.presentation.screen.connection.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fdev.yourdrive.common.util.Empty
import com.fdev.yourdrive.presentation.composable.dialog.AutoBackupDialog
import com.fdev.yourdrive.presentation.composable.dialog.ErrorDialog

@Composable
fun ConnectionDialogs(
    errorDialogMessage: String,
    showErrorDialog: Boolean,
    showBackupDialog: Boolean,
    onErrorDialogDismissed: () -> Unit,
    onBackupDialogConfirmed: () -> Unit,
    onBackupDialogDeclined: () -> Unit
) {
    when {
        showErrorDialog -> ErrorDialog(
            onConfirmClicked = onErrorDialogDismissed,
            errorMessage = errorDialogMessage
        )

        showBackupDialog -> AutoBackupDialog(
            onConfirm = onBackupDialogConfirmed,
            onDecline = onBackupDialogDeclined
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ConnectionDialogs() {
    ConnectionDialogs(
        showErrorDialog = false,
        errorDialogMessage = String.Empty,
        onErrorDialogDismissed = {},
        showBackupDialog = true,
        onBackupDialogConfirmed = {},
        onBackupDialogDeclined = {}
    )
}