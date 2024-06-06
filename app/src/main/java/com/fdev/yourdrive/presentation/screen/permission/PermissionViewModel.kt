package com.fdev.yourdrive.presentation.screen.permission

import com.fdev.yourdrive.presentation.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
) : BaseViewModel<PermissionState, PermissionEvent, PermissionEffect>() {

    override fun onEvent(event: PermissionEvent) {
    }

    override val initialState: PermissionState
        get() = PermissionState()
}