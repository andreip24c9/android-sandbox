package com.example.androidsandbox.presentation.ui.details.widgets

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SandboxToolbar(
    modifier: Modifier = Modifier,
    title: String,
    navigationAction: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.padding(16.dp)
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                        onClick = { navigationAction() })
            )
        })
}

@Composable
@Preview
private fun previewSandboxToolbarLight() {
    AndroidSandboxTheme {
        SandboxToolbar(title = "Toolbar Preview", navigationAction = {})
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
private fun previewSandboxToolbarDark() {
    AndroidSandboxTheme {
        SandboxToolbar(title = "Toolbar Preview", navigationAction = {})
    }
}