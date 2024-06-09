package com.fdev.yourdrive.presentation.composable.editTextField

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fdev.yourdrive.R
import com.fdev.yourdrive.common.util.Empty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextField(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp)),
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    text: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        label = label,
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

@Preview
@Composable
fun EditTextFieldPreview() {
    EditTextField(
        text = String.Empty,
        onValueChange = {}
    ) {
        Text(stringResource(id = R.string.user_name))
    }
}