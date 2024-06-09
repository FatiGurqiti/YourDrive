package com.fdev.yourdrive.presentation.composable.editTextField

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fdev.yourdrive.R
import com.fdev.yourdrive.common.util.Empty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp)),
    password: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit
) {
    var showPassword by remember { mutableStateOf(false) }
    val passwordVisualTransformation = remember { PasswordVisualTransformation() }

    val visualTransformation = if (showPassword) VisualTransformation.None
    else passwordVisualTransformation

    val icon = if (showPassword) Icons.Filled.Visibility
    else Icons.Filled.VisibilityOff

    TextField(
        value = password,
        onValueChange = onValueChange,
        label = label,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = modifier,
        trailingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Toggle password visibility",
                modifier = Modifier.clickable { showPassword = !showPassword })
        }
    )
}

@Preview
@Composable
fun PasswordTextFieldPreview() {
    PasswordTextField(password = String.Empty, onValueChange = {}) {
        Text(text = stringResource(id = R.string.enter_password))
    }
}