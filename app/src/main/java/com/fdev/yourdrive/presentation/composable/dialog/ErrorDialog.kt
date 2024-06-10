package com.fdev.yourdrive.presentation.composable.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fdev.yourdrive.R
import com.fdev.yourdrive.common.util.Empty

@Composable
fun ErrorDialog(
    onConfirmClicked: () -> Unit,
    errorMessage: String
) {
    AlertDialog(
        onDismissRequest = {
        },
        title = {
            Text(text = stringResource(R.string.error))
        },
        text = {
            Text(text = errorMessage)
        },
        confirmButton = {
            Button(
                onClick = onConfirmClicked
            ) {
                Text(
                    text = stringResource(R.string.ok),
                )
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun ErrorDialogPreview() {
    ErrorDialog(onConfirmClicked = { }, errorMessage = String.Empty)
}