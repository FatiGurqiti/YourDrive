package com.fdev.yourdrive.presentation.screen.main

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.fdev.yourdrive.common.manager.permissionManager.PermissionManager.allPermissionsGranted
import com.fdev.yourdrive.presentation.composable.manager.OnLifecycleEvent

@Composable
fun MainLifeCycle(onPermissionsNotGranted: () -> Unit) {
    val activity = LocalContext.current as Activity

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (!activity.allPermissionsGranted()) {
                    onPermissionsNotGranted()
                }
            }

            else -> Unit
        }
    }
}