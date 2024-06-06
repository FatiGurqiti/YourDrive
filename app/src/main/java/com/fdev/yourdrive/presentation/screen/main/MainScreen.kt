package com.fdev.yourdrive.presentation.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fdev.yourdrive.presentation.navigation.Screens
import com.fdev.yourdrive.presentation.navigation.YourDriveNavigation
import com.fdev.yourdrive.presentation.screen.main.composable.MainPermissionManager
import com.fdev.yourdrive.presentation.theme.YourDriveTheme

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val setEvent = viewModel::setEvent

    YourDriveTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainPermissionManager {
                setEvent(MainEvent.SetPermissionsStatus(it))
            }

            YourDriveNavigation(startDestination = Screens.Onboarding)
        }
    }
}