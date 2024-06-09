package com.fdev.yourdrive.presentation.screen.permission

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fdev.yourdrive.common.manager.permissionManager.PermissionManager.allPermissionsGranted
import com.fdev.yourdrive.common.manager.permissionManager.PermissionManager.openAppSettings
import com.fdev.yourdrive.presentation.composable.dialog.PermissionDialog
import com.fdev.yourdrive.presentation.composable.manager.OnLifecycleEvent
import com.fdev.yourdrive.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow

@Composable
fun PermissionScreen(
    navController: NavHostController,
    viewModel: PermissionViewModel = hiltViewModel()
) {
    val setEvent = viewModel::setEvent

    PermissionLifeCycle {
        setEvent(PermissionEvent.OnPermissionsGranted)
    }

    PermissionLaunchedEffect(viewModel.effectTag, viewModel.effect, navController)

    PermissionContent {
        setEvent(PermissionEvent.RequirePermission)
    }
}

@Composable
fun PermissionLaunchedEffect(
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

@Composable
fun PermissionContent(onPermissionClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        PermissionDialog{
            onPermissionClick()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PermissionScreenPreview() {
    PermissionScreen(rememberNavController())
}