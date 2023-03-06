package com.example.androidsandbox.presentation.ui.common.widgets

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme


@Composable
fun SandboxToolbarWithCheckbox(
    modifier: Modifier = Modifier,
    title: String,
    checkboxEnabled: Boolean,
    checked: Boolean,
    navigationAction: () -> Unit,
    onCheckChange: (Boolean) -> Unit
) {
    Row(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {
        SandboxToolbar(
            title = title,
            navigationAction = navigationAction,
            modifier = Modifier.weight(1F)
        )

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckChange,
            enabled = checkboxEnabled,
            modifier = Modifier.align(CenterVertically)
        )
    }
}

@Composable
@Preview
private fun previewSandboxToolbarLight() {
    AndroidSandboxTheme {
        SandboxToolbarWithCheckbox(
            title = "Toolbar Preview",
            checkboxEnabled = true,
            checked = true,
            navigationAction = {},
            onCheckChange = {})
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun previewSandboxToolbarDark() {
    AndroidSandboxTheme {
        SandboxToolbarWithCheckbox(
            title = "Toolbar Preview",
            checkboxEnabled = true,
            checked = true,
            navigationAction = {},
            onCheckChange = {})
    }
}