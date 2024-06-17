package com.fdev.yourdrive.presentation.screen.permission

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.fdev.yourdrive.common.manager.permission.PermissionManager.allPermissionsGranted
import com.fdev.yourdrive.presentation.composable.manager.OnLifecycleEvent

@Composable
fun PermissionLifeCycle(onPermissionsGranted: () -> Unit) {
    val activity = LocalContext.current as Activity

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (activity.allPermissionsGranted()) {
                    onPermissionsGranted()
                }
            }

            else -> Unit
        }
    }
}