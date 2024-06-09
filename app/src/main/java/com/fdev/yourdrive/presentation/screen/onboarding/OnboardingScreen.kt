package com.fdev.yourdrive.presentation.screen.onboarding

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun OnBoardingScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val setEvent = viewModel::setEvent
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        setEvent(OnboardingEvent.OnFinishOnboarding(it))
    }

    OnboardingEffect(viewModel.effectTag, viewModel.effect, navController)
    OnboardingContent(permissionLauncher)
}