package com.fdev.yourdrive.presentation.screen.main

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.fdev.yourdrive.common.manager.permission.PermissionManager.allPermissionsGranted
import kotlinx.coroutines.flow.Flow

@Composable
fun MainEffect(
    effectTag: String,
    effect: Flow<MainEffect>,
    onPermissionDenied: () -> Unit
) {
    val activity = LocalContext.current as Activity

    LaunchedEffect(effectTag) {
        effect.collect { effect ->
            when (effect) {
                MainEffect.CheckPermission -> {
                    if (!activity.allPermissionsGranted()) {
                        onPermissionDenied()
                    }
                }
            }
        }
    }
}