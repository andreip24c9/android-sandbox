package com.example.androidsandbox.presentation.ui.list.widgets

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme

@Composable
fun SandboxFullPageError(
    modifier: Modifier = Modifier,
    errorLabel: String
) {
    Surface(modifier = modifier) {
        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = errorLabel)
        }
    }
}


@Preview
@Composable
private fun SandboxFullPageErrorPreviewLight() {
    AndroidSandboxTheme {
        SandboxFullPageError(
            modifier = Modifier.fillMaxSize(),
            errorLabel = "Oops! Something went wrong!"
        )
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SandboxFullPageErrorPreviewDark() {
    AndroidSandboxTheme {
        SandboxFullPageError(
            modifier = Modifier.fillMaxSize(),
            errorLabel = "Oops! Something went wrong!"
        )
    }
}