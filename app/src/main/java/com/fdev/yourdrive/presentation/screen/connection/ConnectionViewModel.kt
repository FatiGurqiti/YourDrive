package com.fdev.yourdrive.presentation.screen.connection

import com.fdev.yourdrive.presentation.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
) : BaseViewModel<ConnectionState, ConnectionEvent, ConnectionEffect>() {

    override fun onEvent(event: ConnectionEvent) {
    }

    override val initialState: ConnectionState
        get() = ConnectionState()
}