package com.fdev.yourdrive.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fdev.yourdrive.presentation.screen.connection.ConnectionScreen
import com.fdev.yourdrive.presentation.screen.dashboard.DashboardScreen
import com.fdev.yourdrive.presentation.screen.onboarding.OnBoardingScreen
import com.fdev.yourdrive.presentation.screen.permission.PermissionScreen

@Composable
fun YourDriveNavigation(startDestination: Any) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable<Screen.Permission> {
            PermissionScreen()
        }

        composable<Screen.Onboarding> {
            OnBoardingScreen(navController = navController)
        }

        composable<Screen.Connection> {
            ConnectionScreen()
        }

        composable<Screen.Dashboard> {
            DashboardScreen()
        }
    }
}