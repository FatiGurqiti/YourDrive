package com.fdev.yourdrive.presentation.screen.dashboard

import com.fdev.yourdrive.presentation.screen.base.ViewEffect
import com.fdev.yourdrive.presentation.screen.base.ViewEvent
import com.fdev.yourdrive.presentation.screen.base.ViewState

data class DashboardState(
    val showProgressBar: Boolean = false,
    val progress: Double = 0.0
) : ViewState()

sealed class DashboardEvent : ViewEvent()

sealed class DashboardEffect : ViewEffect()