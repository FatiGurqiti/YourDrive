package com.fdev.yourdrive.ui.screen.main

import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.domain.usecase.GetFirstLoadUseCase
import com.fdev.yourdrive.ui.navigation.Screens
import com.fdev.yourdrive.ui.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFirstLoadUseCase: GetFirstLoadUseCase
) : BaseViewModel<MainState, MainEvent, MainEffect>() {

    override val initialState: MainState
        get() = MainState()

    override fun onEvent(event: MainEvent) {
    }

    init {
        setupInitialPage()
    }

    private fun setupInitialPage() {
        viewModelScope.launch {
            when {
                getFirstLoadUseCase() -> {
                    setState { copy(initialScreen = Screens.Onboarding) }
                }
            }
        }
    }
}