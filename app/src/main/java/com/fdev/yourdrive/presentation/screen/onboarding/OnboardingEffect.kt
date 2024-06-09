package com.fdev.yourdrive.presentation.screen.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.Flow

@Composable
fun OnboardingEffect(
    effectTag: String,
    effect: Flow<OnboardingEffect>,
    navController: NavHostController
) {
    LaunchedEffect(effectTag) {
        effect.collect { effect ->
            when (effect) {
                is OnboardingEffect.Navigate -> {
                    navController.navigate(effect.value)
                }
            }
        }
    }
}