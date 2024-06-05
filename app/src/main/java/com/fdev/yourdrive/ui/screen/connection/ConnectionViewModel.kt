package com.fdev.yourdrive.ui.screen.connection

import com.fdev.yourdrive.ui.screen.base.BaseViewModel
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