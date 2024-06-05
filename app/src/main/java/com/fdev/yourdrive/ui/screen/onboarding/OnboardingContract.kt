package com.fdev.yourdrive.ui.screen.onboarding

import com.fdev.yourdrive.ui.screen.base.ViewEffect
import com.fdev.yourdrive.ui.screen.base.ViewEvent
import com.fdev.yourdrive.ui.screen.base.ViewState

class OnboardingState : ViewState()

sealed class OnboardingEvent : ViewEvent()

sealed class OnboardingEffect : ViewEffect()