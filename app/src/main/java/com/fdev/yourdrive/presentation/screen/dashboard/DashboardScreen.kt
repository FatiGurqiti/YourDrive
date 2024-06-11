package com.fdev.yourdrive.presentation.screen.dashboard

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    DashboardEffect(
        effectTag = viewModel.effectTag,
        effect = viewModel.effect
    )

    DashboardContent(
        backupCompleted = state.value.backupCompleted,
        showProgressBar = state.value.showProgressBar,
        progress = state.value.progress
    )
}
