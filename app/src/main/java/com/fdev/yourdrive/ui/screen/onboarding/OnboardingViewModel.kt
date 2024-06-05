package com.fdev.yourdrive.ui.screen.onboarding

import com.fdev.yourdrive.ui.screen.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
) : BaseViewModel<OnboardingState, OnboardingEvent, OnboardingEffect>() {

    override fun onEvent(event: OnboardingEvent) {
    }

    override val initialState: OnboardingState
        get() = OnboardingState()
}