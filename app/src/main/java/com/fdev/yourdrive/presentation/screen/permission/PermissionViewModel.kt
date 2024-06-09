package com.fdev.yourdrive.presentation.screen.permission

import com.fdev.yourdrive.presentation.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
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
        val connectionStatus = false //TODO("Handle this")

        if (connectionStatus) PermissionEffect.NavigateToDashboard.setEffect()
        else PermissionEffect.NavigateToConnection.setEffect()
    }
}