package com.fdev.yourdrive.presentation.screen.permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fdev.yourdrive.presentation.composable.dialog.PermissionDialog

@Composable
fun PermissionContent(onPermissionClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        PermissionDialog{
            onPermissionClick()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PermissionScreenPreview() {
    PermissionContent{

    }
}