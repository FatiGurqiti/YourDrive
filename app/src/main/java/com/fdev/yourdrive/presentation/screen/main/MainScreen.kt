package com.fdev.yourdrive.presentation.screen.main

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fdev.yourdrive.common.manager.permissionManager.PermissionManager.allPermissionsGranted
import com.fdev.yourdrive.presentation.composable.manager.OnLifecycleEvent
import com.fdev.yourdrive.presentation.navigation.Screen
import com.fdev.yourdrive.presentation.navigation.YourDriveNavigation
import com.fdev.yourdrive.presentation.theme.YourDriveTheme
import kotlinx.coroutines.flow.Flow

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

@Composable
fun MainLifeCycle(onPermissionsNotGranted: () -> Unit) {
    val activity = LocalContext.current as Activity

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (!activity.allPermissionsGranted()) {
                    onPermissionsNotGranted()
                }
            }

            else -> Unit
        }
    }
}

@Composable
fun MainEffect(
    effectTag: String,
    effect: Flow<MainEffect>,
    navController: NavHostController
) {
    val activity = LocalContext.current as Activity

    LaunchedEffect(effectTag) {
        effect.collect { effect ->
            when (effect) {
                MainEffect.CheckPermission -> {
                    if (!activity.allPermissionsGranted()) {
                        navController.navigate(Screen.Permission)
                    }
                }
            }
        }
    }
}

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