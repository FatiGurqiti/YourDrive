package com.fdev.yourdrive.presentation.screen.permission

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.fdev.yourdrive.common.manager.permission.PermissionManager.openAppSettings
import com.fdev.yourdrive.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow

@Composable
fun PermissionEffect(
    effectTag: String,
    effect: Flow<PermissionEffect>,
    navController: NavHostController
) {
    val activity = LocalContext.current as Activity

    LaunchedEffect(effectTag) {
        effect.collect { effect ->
            when (effect) {
                PermissionEffect.NavigateToConnection -> navController.navigate(Screen.Connection)
                PermissionEffect.NavigateToDashboard -> navController.navigate(Screen.Dashboard)
                PermissionEffect.OpenAppSettings -> activity.openAppSettings()
            }
        }
    }
}