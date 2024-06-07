package com.fdev.yourdrive.presentation.screen.main

import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.domain.usecase.GetFirstLoadUseCase
import com.fdev.yourdrive.presentation.navigation.Screen
import com.fdev.yourdrive.presentation.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFirstLoadUseCase: GetFirstLoadUseCase
) : BaseViewModel<MainState, MainEvent, MainEffect>() {

    private val connectionActive = false // TODO("Implement this")

    override val initialState: MainState
        get() = MainState()

    override fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SetPermissionsStatus -> setPermissionsStatus(event.value)
        }
    }

    init {
        setupInitialPage()
    }

    private fun setupInitialPage() {
        viewModelScope.launch {
            state.collect {
                val screen : Any = when {
                    getFirstLoadUseCase() -> Screen.Onboarding
                    !it.permissionsGiven -> Screen.Permission
                    connectionActive -> Screen.Dashboard
                    else -> Screen.Connection
                }

                setState { copy(initialScreen = screen, keepSplash = false) }
            }
        }
    }

    private fun setPermissionsStatus(value: Boolean) {
        setState { copy(permissionsGiven = value) }
    }
}