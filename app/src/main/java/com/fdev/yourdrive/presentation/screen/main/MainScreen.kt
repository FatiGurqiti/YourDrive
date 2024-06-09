package com.fdev.yourdrive.presentation.screen.main

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val setEvent = viewModel::setEvent
    val navController = rememberNavController()

    MainEffect(
        effectTag = viewModel.effectTag,
        effect = viewModel.effect,
        navController = navController
    )

    MainLifeCycle {
        setEvent(MainEvent.InitialPermissionCheck)
    }

    MainContent(state, navController)
}