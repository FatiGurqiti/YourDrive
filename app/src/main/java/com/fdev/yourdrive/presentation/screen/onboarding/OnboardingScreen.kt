package com.fdev.yourdrive.presentation.screen.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fdev.yourdrive.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow

@Composable
fun OnBoardingScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    OnboardingLaunchedEffect(viewModel.effectTag, viewModel.effect, navController)
    OnboardingContent(viewModel::setEvent)
}

@Composable
fun OnboardingLaunchedEffect(
    effectTag: String,
    effect: Flow<OnboardingEffect>,
    navController: NavHostController
) {
    LaunchedEffect(effectTag) {
        effect.collect { effect ->
            when (effect) {
                OnboardingEffect.RedirectToConnection -> {
                    navController.navigate(Screen.Connection)
                }
            }
        }
    }
}

@Composable
fun OnboardingContent(setEvent: (OnboardingEvent) -> Unit) {
    Column {
        Text("ojj!  this is onboarding")

        Button(onClick = {
            setEvent(OnboardingEvent.ChangeFirstLaunchStatus)
        }) {
            Text(text = "Finish onboarding")
        }
    }
}