package com.fdev.yourdrive.presentation.screen.onboarding

import com.fdev.yourdrive.presentation.screen.base.ViewEffect
import com.fdev.yourdrive.presentation.screen.base.ViewEvent
import com.fdev.yourdrive.presentation.screen.base.ViewState

class OnboardingState : ViewState()

sealed class OnboardingEvent : ViewEvent()

sealed class OnboardingEffect : ViewEffect()