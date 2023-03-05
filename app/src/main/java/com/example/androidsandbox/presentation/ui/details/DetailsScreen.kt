package com.example.androidsandbox.presentation.ui.details

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidsandbox.presentation.ui.details.widgets.SandboxToolbar
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme

@Composable
fun DetailsScreen(
    uiState: DetailsScreenUiState.Data,
    uiActions: DetailsScreenUiActions,
) {
    Scaffold(topBar = {
        SandboxToolbar(title = uiState.title, navigationAction = uiActions::onNavigateBackClick)
    }) { paddingValues ->
        Surface {
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Details Page"
                )
            }
        }
    }
}

@Composable
@Preview
private fun previewDetailsScreenLight() {
    AndroidSandboxTheme {
        DetailsScreen(
            uiState = DetailsScreenUiState.Data(title = "Preview Title", checked = false),
            uiActions = object : DetailsScreenUiActions {
                override fun onNavigateBackClick() {

                }

                override fun onCheckChange(checked: Boolean) {
                }
            }
        )
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
private fun previewDetailsScreenDark() {
    AndroidSandboxTheme {
        DetailsScreen(
            uiState = DetailsScreenUiState.Data(title = "Preview Title", checked = false),
            uiActions = object : DetailsScreenUiActions {
                override fun onNavigateBackClick() {

                }

                override fun onCheckChange(checked: Boolean) {
                }
            }
        )
    }
}
