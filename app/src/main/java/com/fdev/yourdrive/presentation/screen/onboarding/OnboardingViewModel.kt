package com.fdev.yourdrive.presentation.screen.onboarding

import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.domain.usecase.UpdateFirstLoadUseCase
import com.fdev.yourdrive.presentation.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val updateFirstLoadUseCase: UpdateFirstLoadUseCase
) : BaseViewModel<OnboardingState, OnboardingEvent, OnboardingEffect>() {

    override fun onEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.ChangeFirstLaunchStatus -> changeFirstLaunchStatus()
        }
    }

    override val initialState: OnboardingState
        get() = OnboardingState()

    private fun changeFirstLaunchStatus() {
        viewModelScope.launch {
            updateFirstLoadUseCase()
            OnboardingEffect.RedirectToConnection.setEffect()
        }
    }
}