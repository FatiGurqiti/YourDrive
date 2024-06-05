package com.fdev.yourdrive.ui.screen.permission

import com.fdev.yourdrive.ui.screen.base.ViewEffect
import com.fdev.yourdrive.ui.screen.base.ViewEvent
import com.fdev.yourdrive.ui.screen.base.ViewState

class PermissionState : ViewState()

sealed class PermissionEvent : ViewEvent()

sealed class PermissionEffect : ViewEffect()