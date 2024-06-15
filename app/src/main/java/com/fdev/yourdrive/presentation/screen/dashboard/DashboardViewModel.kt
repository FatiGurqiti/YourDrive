package com.fdev.yourdrive.presentation.screen.dashboard

import com.fdev.yourdrive.domain.usecase.backupService.BackupServiceUseCases
import com.fdev.yourdrive.presentation.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val backupServiceUseCases: BackupServiceUseCases
) : BaseViewModel<DashboardState, DashboardEvent, DashboardEffect>() {

    init {
        DashboardEffect.RequireNotificationPermission.setEffect()
        backup()
    }

    override fun onEvent(event: DashboardEvent) {
    }

    override val initialState: DashboardState
        get() = DashboardState()

    private fun backup() {
        backupServiceUseCases.startBackupServiceUseCase()
    }
}