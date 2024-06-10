package com.fdev.yourdrive.presentation.screen.dashboard

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = hiltViewModel()) {
    Button(onClick = {
        viewModel.backup()
    }) {
        Text("Backup")
    }
}