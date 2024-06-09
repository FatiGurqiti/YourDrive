package com.fdev.yourdrive.presentation.screen.connection

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

@Composable
fun ConnectionScreen(
    viewModel: ConnectionViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val setEvent = viewModel::setEvent

    ConnectionEffect(
        effectTag = viewModel.effectTag,
        effect = viewModel.effect,
        navController = navController
    )

    ConnectionContent(
        showDialog = state.value.showDialog,
        autoBackupChecked = state.value.checkboxChecked,
        remoteURLError = state.value.remoteURLError,
        remoteURL = state.value.remoteURL,
        username = state.value.username,
        password = state.value.password,
        onConnectClicked = { setEvent(ConnectionEvent.OnConnectClicked) },
        onDialogConfirmed = { setEvent(ConnectionEvent.OnDialogConfirmed) },
        onDialogDeclined = { setEvent(ConnectionEvent.OnDialogDeclined) },
        onRemoteURLEntry = { setEvent(ConnectionEvent.OnRemoteURLEntry(it)) },
        onUsernameEntry = { setEvent(ConnectionEvent.OnUsernameEntry(it)) },
        onPasswordEntry = { setEvent(ConnectionEvent.OnPasswordEntry(it)) },
        onCheckboxChecked = { setEvent(ConnectionEvent.OnCheckboxChecked(it)) }
    )
}