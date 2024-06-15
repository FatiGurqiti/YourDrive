package com.fdev.yourdrive.presentation.screen.onboarding

import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.domain.usecase.firstLoad.UpdateFirstLoadUseCase
import com.fdev.yourdrive.presentation.navigation.Screen
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
            is OnboardingEvent.OnFinishOnboarding -> {
                changeFirstLaunchStatus()
                permissionsCheck(event.value)
            }
        }
    }

    override val initialState: OnboardingState
        get() = OnboardingState()

    private fun changeFirstLaunchStatus() {
        viewModelScope.launch {
            updateFirstLoadUseCase()
        }
    }

    private fun permissionsCheck(value: Map<String, Boolean>) {
        val allPermissionsGranted = !value.values.contains(false)
        val navigationScreen: Any = if (allPermissionsGranted) Screen.Connection
        else Screen.Permission

        OnboardingEffect.Navigate(navigationScreen).setEffect()
    }
}