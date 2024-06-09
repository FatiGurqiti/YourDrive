package com.fdev.yourdrive.presentation.screen.permission

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun PermissionScreen(
    navController: NavHostController,
    viewModel: PermissionViewModel = hiltViewModel()
) {
    val setEvent = viewModel::setEvent

    PermissionLifeCycle {
        setEvent(PermissionEvent.OnPermissionsGranted)
    }

    PermissionEffect(viewModel.effectTag, viewModel.effect, navController)

    PermissionContent {
        setEvent(PermissionEvent.RequirePermission)
    }
}