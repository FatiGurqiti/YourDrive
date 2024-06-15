package com.fdev.yourdrive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.fdev.yourdrive.presentation.screen.main.MainScreen
import com.fdev.yourdrive.presentation.screen.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
        setComposable()
    }

    private fun setup() {
        initSplash()
    }

    private fun initSplash() {
        val keepSplash = mutableStateOf(true)
        setKeepSplashState(keepSplash)

        installSplashScreen().setKeepOnScreenCondition {
            keepSplash.value
        }
    }

    private fun setKeepSplashState(keepSplash: MutableState<Boolean>) {
        lifecycleScope.launch {
            viewModel.state.collect {
//                keepSplash.value = it.permissionsGiven == null || it.initialScreen == null
                keepSplash.value = false
            }
        }
    }

    private fun setComposable() {
        setContent {
            MainScreen(viewModel)
        }
    }
}