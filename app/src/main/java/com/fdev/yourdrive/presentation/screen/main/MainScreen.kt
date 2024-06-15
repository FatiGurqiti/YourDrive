package com.fdev.yourdrive.presentation.screen.main

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.fdev.yourdrive.presentation.navigation.Screen

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val setEvent = viewModel::setEvent
    val navController = rememberNavController()

    MainEffect(
        effectTag = viewModel.effectTag,
        effect = viewModel.effect,
        onPermissionDenied = {
            setEvent(MainEvent.SetPermissionsStatus(false))
            navController.navigate(Screen.Permission)
        }
    )

    MainLifeCycle(
        onPermissionsGranted = {
            setEvent(MainEvent.SetPermissionsStatus(true))
        },
        onPermissionsNotGranted = {
            setEvent(MainEvent.InitialPermissionCheck)
        }
    )

    MainContent(state, navController)
}