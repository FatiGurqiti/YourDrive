package com.fdev.yourdrive.presentation.screen.main

import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.domain.usecase.backupStatus.GetBackupStatusUseCase
import com.fdev.yourdrive.domain.usecase.firstLoad.GetFirstLoadUseCase
import com.fdev.yourdrive.presentation.navigation.Screen
import com.fdev.yourdrive.presentation.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFirstLoadUseCase: GetFirstLoadUseCase,
    private val getBackupStatusUseCase: GetBackupStatusUseCase,
) : BaseViewModel<MainState, MainEvent, MainEffect>() {

    init {
        setupInitialPage()
    }

    override val initialState: MainState
        get() = MainState()

    override fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SetPermissionsStatus -> setPermissionsStatus(event.value)
            MainEvent.InitialPermissionCheck -> initialPermissionCheck()
        }
    }

    private fun setupInitialPage() {
        viewModelScope.launch {
            state.collect {
                val screen: Any = when {
                    getFirstLoadUseCase() -> Screen.Onboarding
                    it.permissionsGiven == false -> Screen.Permission
                    getBackupStatusUseCase() -> Screen.Dashboard
                    else -> Screen.Connection
                }

                setState { copy(initialScreen = screen) }
            }
        }
    }

    private fun initialPermissionCheck() {
        viewModelScope.launch {
            if (!getFirstLoadUseCase()) { // Initial launch redirect to onboarding and the permission logic is handled there
                MainEffect.CheckPermission.setEffect()
            }
            else {
                setPermissionsStatus(false)
            }
        }
    }

    private fun setPermissionsStatus(value: Boolean) {
        setState { copy(permissionsGiven = value) }
    }
}