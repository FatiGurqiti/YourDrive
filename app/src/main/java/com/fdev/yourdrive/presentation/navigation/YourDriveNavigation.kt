package com.fdev.yourdrive.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fdev.yourdrive.presentation.screen.connection.ConnectionScreen
import com.fdev.yourdrive.presentation.screen.dashboard.DashboardScreen
import com.fdev.yourdrive.presentation.screen.onboarding.OnBoardingScreen
import com.fdev.yourdrive.presentation.screen.permission.PermissionScreen

@Composable
fun YourDriveNavigation(startDestination: Any, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Permission> {
            PermissionScreen(navController = navController)
        }

        composable<Screen.Onboarding> {
            OnBoardingScreen(navController = navController)
        }

        composable<Screen.Connection> {
            ConnectionScreen(navController = navController)
        }

        composable<Screen.Dashboard> {
            DashboardScreen()
        }
    }
}