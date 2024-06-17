package com.fdev.yourdrive.common.manager.permission

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

object PermissionManager {
    val photosAndVideosPermissions =
        when {
            Build.VERSION.SDK_INT == Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
                arrayOf(
                    Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED, // Access to photos and videos are allowed partially
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
                )
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
                )
            }

            else -> {
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
        }

    fun Activity.allPermissionsGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) checkPermissionForNewerAndroidVersions()
        else checkPermissionForOlderAndroidVersions()
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun Activity.checkPermissionForNewerAndroidVersions(): Boolean {
        for (permission in photosAndVideosPermissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                return true
            }
        }
        return false
    }

    private fun Activity.checkPermissionForOlderAndroidVersions(): Boolean {
        for (permission in photosAndVideosPermissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            ) {
                return false
            }
        }
        return true
    }

    fun Activity.openAppSettings() {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        ).also(::startActivity)
    }
}