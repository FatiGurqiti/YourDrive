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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
fun ConnectionContent(onConnectClicked: () -> Unit) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var url by rememberSaveable { mutableStateOf(String.Empty) }
    var username by rememberSaveable { mutableStateOf(String.Empty) }
    var password by rememberSaveable { mutableStateOf(String.Empty) }
    var autoBackupCheck by rememberSaveable { mutableStateOf(false) }

    if (showDialog) {
        AutoBackupDialog(
            onConfirm = {
                autoBackupCheck = true
                showDialog = false
            },
            onDecline = {
                showDialog = false
            }
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
                text = url,
                onValueChange = { url = it }) {
                Text(stringResource(id = R.string.remote_url))
            }

            EditTextField(
                text = username,
                onValueChange = { username = it }) {
                Text(stringResource(id = R.string.user_name))
            }

            Column {
                PasswordTextField(
                    password = password,
                    onValueChange = { password = it }
                ) {
                    Text(stringResource(id = R.string.enter_password))
                }

                CheckboxField(isChecked = autoBackupCheck, onCheckedChange = {
                    showDialog = it
                    if (!it) autoBackupCheck = false
                })
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
    ConnectionContent {}
}