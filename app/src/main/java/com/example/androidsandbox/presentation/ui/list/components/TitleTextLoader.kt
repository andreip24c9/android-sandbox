package com.example.androidsandbox.presentation.ui.list.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme

@Composable
fun TitleTextLoader(modifier: Modifier = Modifier, text: String, isLoading: Boolean) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.weight(1F),
            text = text,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .height(28.dp)
                    .width(28.dp),
                strokeWidth = 3.dp
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewTitleTextDark() {
    AndroidSandboxTheme {
        TitleTextLoader(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
            text = "Preview Title",
            isLoading = true
        )
    }
}

@Preview
@Composable
fun PreviewTitleTextLight() {
    AndroidSandboxTheme {
        TitleTextLoader(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
            text = "Preview Title",
            isLoading = true
        )
    }
}