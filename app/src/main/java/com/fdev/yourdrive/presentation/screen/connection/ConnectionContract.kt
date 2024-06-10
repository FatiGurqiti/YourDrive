package com.fdev.yourdrive.presentation.screen.connection

import com.fdev.yourdrive.common.util.Empty
import com.fdev.yourdrive.presentation.screen.base.ViewEffect
import com.fdev.yourdrive.presentation.screen.base.ViewEvent
import com.fdev.yourdrive.presentation.screen.base.ViewState

data class ConnectionState(
    val showErrorDialog: Boolean = false,
    val showBackupDialog: Boolean = false,
    val checkboxChecked: Boolean = false,
    val remoteURLError: Boolean = false,
    val errorDialogMessage: String = String.Empty,
    val remoteURL: String = String.Empty,
    val username: String = String.Empty,
    val password: String = String.Empty
) : ViewState()

sealed class ConnectionEvent : ViewEvent() {
    data object OnConnectClicked : ConnectionEvent()
    data object OnErrorDialogDismiss : ConnectionEvent()
    data object OnBackupDialogConfirmed : ConnectionEvent()
    data object OnBackupDialogDeclined : ConnectionEvent()
    data class OnRemoteURLEntry(val value: String) : ConnectionEvent()
    data class OnUsernameEntry(val value: String) : ConnectionEvent()
    data class OnPasswordEntry(val value: String) : ConnectionEvent()
    data class OnCheckboxChecked(val value: Boolean) : ConnectionEvent()
}

sealed class ConnectionEffect : ViewEffect() {
    data object NavigateToDashboard : ConnectionEffect()
}