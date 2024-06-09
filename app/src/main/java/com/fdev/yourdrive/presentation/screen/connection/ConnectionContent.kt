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
import com.fdev.yourdrive.presentation.composable.checkbox.CheckboxField
import com.fdev.yourdrive.presentation.composable.dialog.AutoBackupDialog
import com.fdev.yourdrive.presentation.composable.editTextField.EditTextField
import com.fdev.yourdrive.presentation.composable.editTextField.PasswordTextField

@Composable
fun ConnectionContent(
    showDialog: Boolean,
    autoBackupChecked: Boolean,
    remoteURLError: Boolean,
    remoteURL: String,
    username: String,
    password: String,
    onConnectClicked: () -> Unit,
    onDialogConfirmed: () -> Unit,
    onDialogDeclined: () -> Unit,
    onRemoteURLEntry: (String) -> Unit,
    onUsernameEntry: (String) -> Unit,
    onPasswordEntry: (String) -> Unit,
    onCheckboxChecked: (Boolean) -> Unit,
) {

    if (showDialog) {
        AutoBackupDialog(
            onConfirm = onDialogConfirmed,
            onDecline = onDialogDeclined
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
                text = "Add network drive",
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

                CheckboxField(isChecked = autoBackupChecked, onCheckedChange = onCheckboxChecked)
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
        showDialog = true,
        autoBackupChecked = true,
        remoteURLError = true,
        remoteURL = String.Empty,
        username = String.Empty,
        password = String.Empty,
        onConnectClicked = {},
        onDialogConfirmed = {},
        onDialogDeclined = {},
        onRemoteURLEntry = {},
        onUsernameEntry = {},
        onPasswordEntry = {},
        onCheckboxChecked = {}
    )
}