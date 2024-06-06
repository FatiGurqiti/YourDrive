package com.fdev.yourdrive.presentation.screen.main

import com.fdev.yourdrive.presentation.screen.base.ViewEffect
import com.fdev.yourdrive.presentation.screen.base.ViewEvent
import com.fdev.yourdrive.presentation.screen.base.ViewState

data class MainState(
    val initialScreen: Any? = null,
    val keepSplash: Boolean = true, // Show splash screen
    val permissionsGiven: Boolean = false
) : ViewState()

sealed class MainEvent : ViewEvent() {
    data class SetPermissionsStatus(val value: Boolean): MainEvent()
}

sealed class MainEffect : ViewEffect()