package com.fdev.yourdrive.presentation.screen.dashboard

import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.common.util.toProgressStyle
import com.fdev.yourdrive.domain.manager.BackupManager
import com.fdev.yourdrive.presentation.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
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
                .onCompletion {
                    println("hajde progress: ${100.0.toProgressStyle()}")
                }
                .catch {
//                    show error
                }
                .collect{
                println("hajde progress: ${it.toProgressStyle()}")
            }
        }
    }
}