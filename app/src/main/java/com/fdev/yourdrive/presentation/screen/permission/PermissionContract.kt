package com.fdev.yourdrive.presentation.screen.permission

import com.fdev.yourdrive.presentation.screen.base.ViewEffect
import com.fdev.yourdrive.presentation.screen.base.ViewEvent
import com.fdev.yourdrive.presentation.screen.base.ViewState

class PermissionState : ViewState()

sealed class PermissionEvent : ViewEvent() {
    data object RequirePermission : PermissionEvent()
    data object OnPermissionsGranted : PermissionEvent()
}

sealed class PermissionEffect : ViewEffect() {
    data object NavigateToConnection : PermissionEffect()
    data object NavigateToDashboard : PermissionEffect()
    data object OpenAppSettings : PermissionEffect()
}