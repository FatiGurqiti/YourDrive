package com.fdev.yourdrive.ui.screen.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.fdev.yourdrive.ui.navigation.Screens

@Composable
fun OnBoardingScreen(navController: NavHostController) {
    Column {
        Text("ojj!  this is onboarding")

        Button(modifier = Modifier.fillMaxSize(.3f),
            onClick = {
                navController.navigate(Screens.Connection)
            }) {
            Text("Go")
        }
    }
}