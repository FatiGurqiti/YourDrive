package com.fdev.yourdrive.ui.screen.dashboard

import com.fdev.yourdrive.ui.screen.base.ViewEffect
import com.fdev.yourdrive.ui.screen.base.ViewEvent
import com.fdev.yourdrive.ui.screen.base.ViewState

class DashboardState : ViewState()

sealed class DashboardEvent : ViewEvent()

sealed class DashboardEffect : ViewEffect()