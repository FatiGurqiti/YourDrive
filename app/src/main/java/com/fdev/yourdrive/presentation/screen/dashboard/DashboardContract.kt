package com.fdev.yourdrive.presentation.screen.dashboard

import com.fdev.yourdrive.presentation.screen.base.ViewEffect
import com.fdev.yourdrive.presentation.screen.base.ViewEvent
import com.fdev.yourdrive.presentation.screen.base.ViewState

class DashboardState : ViewState()

sealed class DashboardEvent : ViewEvent()

sealed class DashboardEffect : ViewEffect()