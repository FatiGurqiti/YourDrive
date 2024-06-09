package com.fdev.yourdrive.presentation.screen.main

import com.fdev.yourdrive.presentation.screen.base.ViewEffect
import com.fdev.yourdrive.presentation.screen.base.ViewEvent
import com.fdev.yourdrive.presentation.screen.base.ViewState

data class MainState(
    val initialScreen: Any? = null,
    val permissionsGiven: Boolean? = null
) : ViewState()

sealed class MainEvent : ViewEvent() {
    data class SetPermissionsStatus(val value: Boolean) : MainEvent()
    data object InitialPermissionCheck : MainEvent()
}

sealed class MainEffect : ViewEffect() {
    data object CheckPermission : MainEffect()
}