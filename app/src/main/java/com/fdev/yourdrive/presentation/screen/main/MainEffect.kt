package com.fdev.yourdrive.presentation.screen.main

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.fdev.yourdrive.common.manager.permissionManager.PermissionManager.allPermissionsGranted
import com.fdev.yourdrive.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow

@Composable
fun MainEffect(
    effectTag: String,
    effect: Flow<MainEffect>,
    navController: NavHostController
) {
    val activity = LocalContext.current as Activity

    LaunchedEffect(effectTag) {
        effect.collect { effect ->
            when (effect) {
                MainEffect.CheckPermission -> {
                    if (!activity.allPermissionsGranted()) {
                        navController.navigate(Screen.Permission)
                    }
                }
            }
        }
    }
}