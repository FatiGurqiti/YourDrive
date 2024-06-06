package com.fdev.yourdrive.presentation.screen.dashboard

import com.fdev.yourdrive.presentation.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
) : BaseViewModel<DashboardState, DashboardEvent, DashboardEffect>() {

    override fun onEvent(event: DashboardEvent) {
    }

    override val initialState: DashboardState
        get() = DashboardState()
}