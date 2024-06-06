package com.fdev.yourdrive.presentation.screen.permission

import com.fdev.yourdrive.presentation.screen.base.ViewEffect
import com.fdev.yourdrive.presentation.screen.base.ViewEvent
import com.fdev.yourdrive.presentation.screen.base.ViewState

class PermissionState : ViewState()

sealed class PermissionEvent : ViewEvent()

sealed class PermissionEffect : ViewEffect()