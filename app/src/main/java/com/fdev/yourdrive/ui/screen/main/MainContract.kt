package com.fdev.yourdrive.ui.screen.main

import com.fdev.yourdrive.ui.screen.base.ViewEffect
import com.fdev.yourdrive.ui.screen.base.ViewEvent
import com.fdev.yourdrive.ui.screen.base.ViewState

data class MainState(
    val initialScreen: Any? = null,
    val keepSplash: Boolean = true // Show splash screen
) : ViewState()

sealed class MainEvent : ViewEvent()

sealed class MainEffect : ViewEffect()