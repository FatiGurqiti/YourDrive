package com.fdev.yourdrive.presentation.screen.main

import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.domain.usecase.GetFirstLoadUseCase
import com.fdev.yourdrive.presentation.navigation.Screens
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

            val screen : Any = when {
                getFirstLoadUseCase() -> Screens.Onboarding
                !state.value.permissionsGiven -> Screens.Permission
                connectionActive -> Screens.Dashboard
                else -> Screens.Connection
            }

            setState { copy(initialScreen = screen) }
        }
    }

    private fun setPermissionsStatus(value: Boolean) {
        setState { copy(permissionsGiven = value) }
    }
}