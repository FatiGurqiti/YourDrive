package com.fdev.yourdrive.presentation.screen.dashboard

import com.fdev.yourdrive.presentation.screen.base.ViewEffect
import com.fdev.yourdrive.presentation.screen.base.ViewEvent
import com.fdev.yourdrive.presentation.screen.base.ViewState

data class DashboardState(
    val progress: Float = 0f,
    val showProgressBar: Boolean = false,
    val backupCompleted: Boolean = false,
    val alreadySynced: Boolean = false
) : ViewState()

sealed class DashboardEvent : ViewEvent()

sealed class DashboardEffect : ViewEffect() {
    data object RequireNotificationPermission : DashboardEffect()
}