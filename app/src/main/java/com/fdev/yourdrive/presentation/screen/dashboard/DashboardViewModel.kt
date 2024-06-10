package com.fdev.yourdrive.presentation.screen.dashboard

import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.domain.manager.BackupManager
import com.fdev.yourdrive.presentation.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val backupManager: BackupManager
) : BaseViewModel<DashboardState, DashboardEvent, DashboardEffect>() {

    override fun onEvent(event: DashboardEvent) {
    }

    override val initialState: DashboardState
        get() = DashboardState()

    fun backup() {
        viewModelScope.launch {
            backupManager.backup()
        }
    }
}