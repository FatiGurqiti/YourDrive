package com.fdev.yourdrive.presentation.screen.connection

import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.common.util.Empty
import com.fdev.yourdrive.common.util.setNullIfEmpty
import com.fdev.yourdrive.domain.enumeration.Result
import com.fdev.yourdrive.domain.model.NetworkAuth
import com.fdev.yourdrive.domain.usecase.backup.NetworkDriveConnectionUseCase
import com.fdev.yourdrive.presentation.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val networkDriveConnectionUseCase: NetworkDriveConnectionUseCase
) : BaseViewModel<ConnectionState, ConnectionEvent, ConnectionEffect>() {

    override val initialState: ConnectionState
        get() = ConnectionState()

    override fun onEvent(event: ConnectionEvent) {
        when (event) {
            ConnectionEvent.OnConnectClicked -> onConnectClicked()
            ConnectionEvent.OnBackupDialogConfirmed -> onBackupDialogConfirmed()
            ConnectionEvent.OnBackupDialogDeclined -> onBackupDialogDeclined()
            ConnectionEvent.OnErrorDialogDismiss -> onErrorDialogDismiss()
            is ConnectionEvent.OnCheckboxChecked -> onCheckboxChecked(event.value)
            is ConnectionEvent.OnRemoteURLEntry -> onRemoteURLEntry(event.value)
            is ConnectionEvent.OnUsernameEntry -> onUsernameEntry(event.value)
            is ConnectionEvent.OnPasswordEntry -> onPasswordEntry(event.value)
        }
    }

    private fun onCheckboxChecked(value: Boolean) {
        setState { copy(showBackupDialog = value) }
        if (!value) {
            setState { copy(checkboxChecked = false) }
        }
    }

    private fun onBackupDialogConfirmed() {
        setState { copy(checkboxChecked = true, showBackupDialog = false) }
    }

    private fun onBackupDialogDeclined() {
        setState { copy(checkboxChecked = false, showBackupDialog = false) }
    }

    private fun onErrorDialogDismiss() {
        setState { copy(showErrorDialog = false, errorDialogMessage = String.Empty) }
    }

    private fun onRemoteURLEntry(value: String) {
        setState { copy(remoteURL = value) }
    }

    private fun onUsernameEntry(value: String) {
        setState { copy(username = value) }
    }

    private fun onPasswordEntry(value: String) {
        setState { copy(password = value) }
    }

    private fun onConnectClicked() {
        if (checkFieldValues()) {
            startConnection()
        }
    }

    private fun checkFieldValues(): Boolean {
        val emptyRemoteUrl = state.value.remoteURL.isEmpty()
        setState { copy(remoteURLError = emptyRemoteUrl) }

        return !emptyRemoteUrl
    }

    private fun startConnection() {
        val networkAuth = NetworkAuth(
            remoteURL = state.value.remoteURL,
            username = state.value.username.setNullIfEmpty(),
            password = state.value.password.setNullIfEmpty()
        )

        viewModelScope.launch {
            networkDriveConnectionUseCase(networkAuth).also {
                if (it == Result.SUCCESS) {
                    ConnectionEffect.NavigateToDashboard.setEffect()
                } else {
                    setState {
                        copy(
                            showErrorDialog = true,
                            errorDialogMessage = (it as Result.FAILED).reason
                        )
                    }
                }
            }
        }
    }
}