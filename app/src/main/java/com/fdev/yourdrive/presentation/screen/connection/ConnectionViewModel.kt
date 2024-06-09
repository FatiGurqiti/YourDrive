package com.fdev.yourdrive.presentation.screen.connection

import com.fdev.yourdrive.common.util.setNullIfEmpty
import com.fdev.yourdrive.domain.model.NetworkAuth
import com.fdev.yourdrive.presentation.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
) : BaseViewModel<ConnectionState, ConnectionEvent, ConnectionEffect>() {

    override val initialState: ConnectionState
        get() = ConnectionState()

    override fun onEvent(event: ConnectionEvent) {
        when (event) {
            ConnectionEvent.OnConnectClicked -> onConnectClicked()
            ConnectionEvent.OnDialogConfirmed -> onDialogConfirmed()
            ConnectionEvent.OnDialogDeclined -> onDialogDeclined()
            is ConnectionEvent.OnCheckboxChecked -> onCheckboxChecked(event.value)
            is ConnectionEvent.OnRemoteURLEntry -> onRemoteURLEntry(event.value)
            is ConnectionEvent.OnUsernameEntry -> onUsernameEntry(event.value)
            is ConnectionEvent.OnPasswordEntry -> onPasswordEntry(event.value)
        }
    }

    private fun onCheckboxChecked(value: Boolean) {
        setState { copy(showDialog = value) }
        if (!value) {
            setState { copy(checkboxChecked = false) }
        }
    }

    private fun onDialogConfirmed() {
        setState { copy(checkboxChecked = true, showDialog = false) }
    }

    private fun onDialogDeclined() {
        setState { copy(checkboxChecked = false, showDialog = false) }
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
            // call store allow backup state to datastore use case
            startConnection()
            loadCredentialsToFirebase()
            ConnectionEffect.NavigateToDashboard.setEffect()
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
    }

    private fun loadCredentialsToFirebase() {

    }
}