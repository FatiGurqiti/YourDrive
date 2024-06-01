package com.fdev.yourdrive.ui.screen.main

import com.fdev.yourdrive.domain.model.FileData
import com.fdev.yourdrive.ui.screen.base.ViewEffect
import com.fdev.yourdrive.ui.screen.base.ViewEvent
import com.fdev.yourdrive.ui.screen.base.ViewState

data class MainState(
    val album: List<FileData> = emptyList(),
) : ViewState()

sealed class MainEvent : ViewEvent()

sealed class MainEffect : ViewEffect()