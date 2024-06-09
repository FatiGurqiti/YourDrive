package com.fdev.yourdrive.presentation.composable.checkbox

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fdev.yourdrive.R

@Composable
fun CheckboxField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.End,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    headerComposable: @Composable () -> Unit = {
        Text(stringResource(id = R.string.auto_backup))
    }
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        headerComposable()

        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
        )
    }
}

@Preview
@Composable
fun CheckboxFieldPreview() {
    Checkbox(checked = true, onCheckedChange = {})
}