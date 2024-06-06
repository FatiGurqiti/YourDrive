package com.fdev.yourdrive.presentation.screen.connection

import com.fdev.yourdrive.presentation.screen.base.ViewEffect
import com.fdev.yourdrive.presentation.screen.base.ViewEvent
import com.fdev.yourdrive.presentation.screen.base.ViewState

class ConnectionState : ViewState()

sealed class ConnectionEvent : ViewEvent()

sealed class ConnectionEffect : ViewEffect()