package com.fdev.yourdrive.ui.screen.connection

import com.fdev.yourdrive.ui.screen.base.ViewEffect
import com.fdev.yourdrive.ui.screen.base.ViewEvent
import com.fdev.yourdrive.ui.screen.base.ViewState

class ConnectionState : ViewState()

sealed class ConnectionEvent : ViewEvent()

sealed class ConnectionEffect : ViewEffect()