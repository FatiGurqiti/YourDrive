package com.fdev.yourdrive.presentation.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.fdev.yourdrive.presentation.navigation.YourDriveNavigation
import com.fdev.yourdrive.presentation.theme.YourDriveTheme

@Composable
fun MainContent(state: State<MainState>, navController: NavHostController) {
    YourDriveTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            state.value.initialScreen?.let {
                YourDriveNavigation(startDestination = it, navController = navController)
            }
        }
    }
}