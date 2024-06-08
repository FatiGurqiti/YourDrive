package com.fdev.yourdrive.presentation.composable.permission

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_VIDEO
    )
} else {
    arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionManager(result: (Boolean) -> Unit) {
    val permissionState = rememberMultiplePermissionsState(permissions.asList())

    LaunchedEffect(permissionState.permissions) {
        result(permissionState.allPermissionsGranted)
    }
}