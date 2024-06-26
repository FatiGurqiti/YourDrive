package com.fdev.yourdrive.presentation.screen.onboarding

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fdev.yourdrive.R
import com.fdev.yourdrive.common.manager.permission.PermissionManager
import com.fdev.yourdrive.presentation.composable.logo.HexagonLogo

@Composable
fun OnboardingContent(
    permissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>
) {
    var showExplanation by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.oj),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 44.sp
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.onboarding_welcome),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(.4f)
                        .fillMaxHeight(.2f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HexagonLogo()
                }
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.onboarding_introduction),
                textAlign = TextAlign.Start,
                fontSize = 18.sp
            )

            Column {
                Button(onClick = {
                    showExplanation = !showExplanation

                }) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.setup_network_drive),
                        textAlign = TextAlign.Start
                    )
                }

                Spacer(Modifier.fillMaxSize(.1f))

                AnimatedVisibility(visible = showExplanation) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.me_neither),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(onClick = {
                permissionLauncher.launch(PermissionManager.photosAndVideosPermissions)
            }) {
                Text(
                    text = stringResource(R.string.lets_go),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun OnboardingContentPreview() {
    OnboardingContent(
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = {})
    )
}