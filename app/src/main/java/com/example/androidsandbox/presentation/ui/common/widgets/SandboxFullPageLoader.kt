package com.example.androidsandbox.presentation.ui.list.widgets

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme

@Composable
fun SandboxFullPageLoader(
    modifier: Modifier = Modifier,
    loadingLabel: String
) {
    Surface(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = loadingLabel)
            CircularProgressIndicator(modifier = Modifier
                .size(48.dp)
                .padding(top = 16.dp))
        }
    }
}


@Preview
@Composable
private fun SandboxFullPageLoaderPreviewLight() {
    AndroidSandboxTheme {
        SandboxFullPageLoader(
            modifier = Modifier.fillMaxSize(),
            loadingLabel = "Loading please wait ..."
        )
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SandboxFullPageLoaderPreviewDark() {
    AndroidSandboxTheme {
        SandboxFullPageLoader(
            modifier = Modifier.fillMaxSize(),
            loadingLabel = "Loading please wait ..."
        )
    }
}