package com.fdev.yourdrive.presentation.screen.connection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.fdev.yourdrive.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow

@Composable
fun ConnectionEffect(
    effectTag: String,
    effect: Flow<ConnectionEffect>,
    navController: NavHostController
) {
    LaunchedEffect(effectTag) {
        effect.collect { effect ->
            when (effect) {
                ConnectionEffect.NavigateToDashboard -> {
                    navController.navigate(Screen.Dashboard)
                }
            }
        }
    }
}