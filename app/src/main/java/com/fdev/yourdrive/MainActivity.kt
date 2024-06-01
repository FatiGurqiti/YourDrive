package com.fdev.yourdrive

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fdev.yourdrive.ui.screen.main.MainScreen
import com.fdev.yourdrive.ui.theme.YourDriveTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}