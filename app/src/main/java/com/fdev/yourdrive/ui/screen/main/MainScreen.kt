package com.fdev.yourdrive.ui.screen.main

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.fdev.yourdrive.ui.navigation.Screens
import com.fdev.yourdrive.ui.navigation.YourDriveNavigation
import com.fdev.yourdrive.ui.theme.YourDriveTheme

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val state: MainState by viewModel.state.collectAsStateWithLifecycle()

    YourDriveTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            YourDriveNavigation(startDestination = Screens.Onboarding)
        }
    }
}