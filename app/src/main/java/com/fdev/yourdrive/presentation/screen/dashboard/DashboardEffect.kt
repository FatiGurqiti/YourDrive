package com.fdev.yourdrive.presentation.screen.dashboard

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import android.Manifest
import android.os.Build
import kotlinx.coroutines.flow.Flow

@Composable
fun DashboardEffect(
    effectTag: String,
    effect: Flow<DashboardEffect>,
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {

    }

    LaunchedEffect(effectTag) {
        effect.collect { effect ->
            when (effect) {
                DashboardEffect.RequireNotificationPermission -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }
        }
    }
}