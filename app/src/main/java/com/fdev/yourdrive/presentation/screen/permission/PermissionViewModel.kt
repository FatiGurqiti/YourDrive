package com.fdev.yourdrive.presentation.screen.permission

import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.domain.usecase.backupStatus.GetBackupStatusUseCase
import com.fdev.yourdrive.presentation.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    private val getBackupStatusUseCase: GetBackupStatusUseCase
) : BaseViewModel<PermissionState, PermissionEvent, PermissionEffect>() {

    override val initialState: PermissionState
        get() = PermissionState()

    override fun onEvent(event: PermissionEvent) {
        when (event) {
            is PermissionEvent.OnPermissionsGranted -> onPermissionsGranted()
            PermissionEvent.RequirePermission -> PermissionEffect.OpenAppSettings.setEffect()
        }
    }

    private fun onPermissionsGranted() {
        viewModelScope.launch {
            if (getBackupStatusUseCase()) PermissionEffect.NavigateToDashboard.setEffect()
            else PermissionEffect.NavigateToConnection.setEffect()
        }
    }
}