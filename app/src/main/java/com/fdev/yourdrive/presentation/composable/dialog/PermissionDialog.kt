package com.fdev.yourdrive.presentation.composable.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fdev.yourdrive.R

@Composable
fun PermissionDialog(onConfirmClicked: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
        },
        title = {
            Text(text = stringResource(R.string.permission_required))
        },
        text = {
            Text(text = stringResource(R.string.permission_text))
        },
        confirmButton = {
            Button(
                onClick = onConfirmClicked
            ) {
                Text(
                    text = stringResource(R.string.go_to_settings),
                )
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun PermissionDialogPreview() {
    PermissionDialog {}
}