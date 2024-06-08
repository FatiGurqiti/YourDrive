package com.fdev.yourdrive.presentation.screen.onboarding

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fdev.yourdrive.R
import com.fdev.yourdrive.presentation.composable.permission.permissions
import kotlinx.coroutines.flow.Flow

@Composable
fun OnBoardingScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val setEvent = viewModel::setEvent
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        setEvent(OnboardingEvent.OnFinishOnboarding(it))
    }

    OnboardingLaunchedEffect(viewModel.effectTag, viewModel.effect, navController)
    OnboardingContent(permissionLauncher)
}

@Composable
fun OnboardingLaunchedEffect(
    effectTag: String,
    effect: Flow<OnboardingEffect>,
    navController: NavHostController
) {
    LaunchedEffect(effectTag) {
        effect.collect { effect ->
            when (effect) {
                is OnboardingEffect.Navigate -> {
                    navController.navigate(effect.value)
                }
            }
        }
    }
}

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
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.8f)
                .padding(horizontal = 40.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
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
                permissionLauncher.launch(permissions)
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
fun OnboardingScreenPreview() {
    OnBoardingScreen(rememberNavController())
}