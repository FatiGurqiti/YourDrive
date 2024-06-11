package com.fdev.yourdrive.presentation.screen.dashboard

import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.domain.manager.BackupManager
import com.fdev.yourdrive.presentation.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val backupManager: BackupManager
) : BaseViewModel<DashboardState, DashboardEvent, DashboardEffect>() {

    init {
        backup()
    }

    override fun onEvent(event: DashboardEvent) {
    }

    override val initialState: DashboardState
        get() = DashboardState()

    private fun backup() {
        viewModelScope.launch {
            backupManager.backup()
                .onStart {
                    setState { copy(showProgressBar = true) }
                }
                .onCompletion {
                    setState { copy(backupCompleted = true, showProgressBar = false) }
                }
                .catch {
                    setState { copy(showProgressBar = false) }
                }
                .collect {
                    setState { copy(progress = it) }
                }
        }
    }
}