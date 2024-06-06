package com.fdev.yourdrive.ui.screen.main

import com.fdev.yourdrive.ui.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainState, MainEvent, MainEffect>() {

    override val initialState: MainState
        get() = MainState()

    override fun onEvent(event: MainEvent) {
    }

    init {
        setState { copy(keepSplash = false) }
    }
}