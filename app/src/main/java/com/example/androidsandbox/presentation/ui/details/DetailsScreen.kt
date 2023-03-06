package com.example.androidsandbox.presentation.ui.details

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidsandbox.presentation.ui.common.widgets.SandboxToolbar
import com.example.androidsandbox.presentation.ui.common.widgets.SandboxToolbarWithCheckbox
import com.example.androidsandbox.presentation.ui.list.widgets.SandboxFullPageError
import com.example.androidsandbox.presentation.ui.list.widgets.SandboxFullPageLoader
import com.example.androidsandbox.presentation.ui.theme.AndroidSandboxTheme

@Composable
fun DetailsScreen(
    uiState: DetailsScreenUiState,
    uiActions: DetailsScreenUiActions,
) {
    Scaffold(topBar = {
        SandboxToolbarWithCheckbox(
            title = (uiState as? DetailsScreenUiState.Data)?.title ?: "",
            navigationAction = uiActions::onNavigateBackClick,
            checkboxEnabled = uiState is DetailsScreenUiState.Data,
            checked = (uiState as? DetailsScreenUiState.Data)?.checked ?: false,
            onCheckChange = uiActions::onCheckChange
        )
    }) { paddingValues ->
        Surface {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                when (uiState) {
                    is DetailsScreenUiState.Data -> {
                        Text(text = uiState.description, modifier = Modifier.padding(16.dp))
                    }
                    DetailsScreenUiState.Error -> {
                        SandboxFullPageError(
                            modifier = Modifier.fillMaxSize(),
                            errorLabel = "Error"
                        )
                    }
                    DetailsScreenUiState.Loading -> {
                        SandboxFullPageLoader(
                            modifier = Modifier.fillMaxSize(),
                            loadingLabel = "Loading ..."
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun previewDetailsScreenLight() {
    AndroidSandboxTheme {
        DetailsScreen(
            uiState = DetailsScreenUiState.Data(
                title = "Preview Title",
                description = "Description",
                checked = false
            ),
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
            uiState = DetailsScreenUiState.Data(
                title = "Preview Title",
                description = "Description",
                checked = false
            ),
            uiActions = object : DetailsScreenUiActions {
                override fun onNavigateBackClick() {

                }

                override fun onCheckChange(checked: Boolean) {
                }
            }
        )
    }
}
