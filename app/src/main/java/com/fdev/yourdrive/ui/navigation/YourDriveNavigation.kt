package com.fdev.yourdrive.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fdev.yourdrive.ui.screen.connection.ConnectionScreen
import com.fdev.yourdrive.ui.screen.dashboard.DashboardScreen
import com.fdev.yourdrive.ui.screen.onboarding.OnBoardingScreen
import com.fdev.yourdrive.ui.screen.permission.PermissionScreen

@Composable
fun YourDriveNavigation(startDestination: Any) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable<Screens.Permission> {
            PermissionScreen()
        }

        composable<Screens.Onboarding> {
            OnBoardingScreen(navController = navController)
        }

        composable<Screens.Connection> {
            ConnectionScreen()
        }

        composable<Screens.Dashboard> {
            DashboardScreen()
        }
    }
}