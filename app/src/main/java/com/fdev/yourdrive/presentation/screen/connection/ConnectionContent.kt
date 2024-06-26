package com.fdev.yourdrive.presentation.screen.connection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fdev.yourdrive.R
import com.fdev.yourdrive.common.util.Empty
import com.fdev.yourdrive.presentation.composable.dialog.ErrorDialog
import com.fdev.yourdrive.presentation.composable.editTextField.EditTextField
import com.fdev.yourdrive.presentation.composable.editTextField.PasswordTextField

@Composable
fun ConnectionContent(
    showErrorDialog: Boolean,
    remoteURLError: Boolean,
    errorDialogMessage: String,
    remoteURL: String,
    username: String,
    password: String,
    onConnectClicked: () -> Unit,
    onErrorDialogDismissed: () -> Unit,
    onRemoteURLEntry: (String) -> Unit,
    onUsernameEntry: (String) -> Unit,
    onPasswordEntry: (String) -> Unit,
) {

    if (showErrorDialog) {
        ErrorDialog(
            onConfirmClicked = onErrorDialogDismissed,
            errorMessage = errorDialogMessage
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.connect_to_a_network_drive),
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            EditTextField(
                text = remoteURL,
                onValueChange = onRemoteURLEntry,
                isError = remoteURLError
            ) {
                Text(stringResource(id = R.string.remote_url))
            }

            EditTextField(
                text = username,
                onValueChange = onUsernameEntry
            ) {
                Text(stringResource(id = R.string.user_name))
            }

            Column {
                PasswordTextField(
                    password = password,
                    onValueChange = onPasswordEntry
                ) {
                    Text(stringResource(id = R.string.enter_password))
                }
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onConnectClicked
        ) {
            Text(text = stringResource(id = R.string.connect))
        }
    }
}

@Preview
@Composable
fun ConnectionContentPreview() {
    ConnectionContent(
        showErrorDialog = false,
        remoteURLError = true,
        errorDialogMessage = String.Empty,
        remoteURL = String.Empty,
        username = String.Empty,
        password = String.Empty,
        onConnectClicked = {},
        onErrorDialogDismissed = {},
        onRemoteURLEntry = {},
        onUsernameEntry = {},
        onPasswordEntry = {},
    )
}